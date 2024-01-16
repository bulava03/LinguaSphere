package com.example.LinguaSphere.web;

import com.example.LinguaSphere.config.security.JwtTokenService;
import com.example.LinguaSphere.entity.*;
import com.example.LinguaSphere.entity.dto.LessonTemplate;
import com.example.LinguaSphere.entity.dto.RequestDto;
import com.example.LinguaSphere.entity.dto.UserDto;
import com.example.LinguaSphere.helper.UserHelper;
import com.example.LinguaSphere.service.*;
import com.example.LinguaSphere.service.impl.UserDetailsServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private LanguageService languageService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherLanguageService teacherLanguageService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtTokenService jwtTokenService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserHelper userHelper = new UserHelper();


    @GetMapping("userPage")
    public String userPage(@ModelAttribute("request") RequestDto request, Model model) {
        String payload = userHelper.decodeToken(request.getToken());
        JsonNode node;
        String username;
        try {
            node = objectMapper.readTree(payload);
            username = node.get("sub").asText();
        } catch (Exception ex) {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }

        User userFounded = userService.findByEmail(username).orElse(null);

        if (userFounded != null) {
            UserDto userDto = modelMapper.map(userFounded, UserDto.class);
            userDto.setDateOfBirth(userHelper.formDate(userFounded));
            model.addAttribute("user", userDto);
            model.addAttribute("token", request.getToken());
            return "user/userPage";
        } else {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }
    }

    @GetMapping("userSchedule")
    public String getUserSchedule(@ModelAttribute("request") RequestDto request, Model model) {
        String payload = userHelper.decodeToken(request.getToken());
        JsonNode node;
        String username;
        try {
            node = objectMapper.readTree(payload);
            username = node.get("sub").asText();
        } catch (Exception ex) {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }

        User userFounded = userService.findByEmail(username).orElse(null);

        if (userFounded != null) {
            UserDto userDto = modelMapper.map(userFounded, UserDto.class);
            userDto.setDateOfBirth(userHelper.formDate(userFounded));

            int[][] lessons = userHelper.getUsersLessons(lessonService.findAllByUserId(userFounded.getId()));

            model.addAttribute("user", userDto);
            model.addAttribute("lessons", lessons);
            model.addAttribute("token", request.getToken());
            return "user/userSchedule";
        } else {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }
    }

    @GetMapping("getLanguageChoosingPage")
    public String getLanguageChoosingPage(@ModelAttribute("request") RequestDto request, Model model) {
        String payload = userHelper.decodeToken(request.getToken());
        JsonNode node;
        String username;
        try {
            node = objectMapper.readTree(payload);
            username = node.get("sub").asText();
        } catch (Exception ex) {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }

        User userFounded = userService.findByEmail(username).orElse(null);

        if (userFounded != null) {
            UserDto userDto = modelMapper.map(userFounded, UserDto.class);
            userDto.setDateOfBirth(userHelper.formDate(userFounded));

            model.addAttribute("user", userDto);
            model.addAttribute("languages", languageService.findAll());
            model.addAttribute("token", request.getToken());
            return "user/choosingLanguagePage";
        } else {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }
    }

    @GetMapping("submitChooseLanguageForm")
    public String submitChooseLanguageForm(RequestDto request, LessonTemplate lesson, Model model) {
        String payload = userHelper.decodeToken(request.getToken());
        JsonNode node;
        String username;
        try {
            node = objectMapper.readTree(payload);
            username = node.get("sub").asText();
        } catch (Exception ex) {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }

        User userFounded = userService.findByEmail(username).orElse(null);

        if (userFounded != null) {
            UserDto userDto = modelMapper.map(userFounded, UserDto.class);
            userDto.setDateOfBirth(userHelper.formDate(userFounded));

            List<TeacherLanguage> teacherLanguages = teacherLanguageService.findAllByLanguageId(lesson.getLanguageId());
            List<Long> list = new ArrayList<>();
            for (TeacherLanguage element : teacherLanguages
                 ) {
                list.add(element.getTeacherId());
            }
            List<Lesson> lessons = lessonService.findAllByTeacherIds(list);
            List<Lesson> usersLessons = lessonService.findAllByUserId(userFounded.getId());
            lessons.removeIf(element -> element.getUserId() != null);
            for (Lesson element : usersLessons
                 ) {
                lessons.removeIf(elem -> elem.getDay() == element.getDay() && elem.getTime() == element.getTime());
            }

            List<LessonTemplate> lessonList = new ArrayList<>();
            for (Lesson element : lessons
                 ) {
                Teacher teacherTemp = teacherService.findById(element.getTeacherId());
                String teacherName = teacherTemp.getName() + " " + teacherTemp.getSurname();
                String date = userHelper.convertIntIntoDate(element.getDay(), element.getTime());
                lessonList.add(new LessonTemplate(element.getId(), lesson.getLanguageId(), element.getTeacherId(),
                        teacherName, date));
            }

            model.addAttribute("user", userDto);
            model.addAttribute("lessons", lessonList);
            model.addAttribute("languageId", lesson.getLanguageId());
            model.addAttribute("token", request.getToken());
            return "user/choosingLessonPage";
        } else {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }
    }

    @PostMapping("setLesson")
    public String setLesson(RequestDto request, LessonTemplate lesson, Model model) {
        String payload = userHelper.decodeToken(request.getToken());
        JsonNode node;
        String username;
        try {
            node = objectMapper.readTree(payload);
            username = node.get("sub").asText();
        } catch (Exception ex) {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }

        User userFounded = userService.findByEmail(username).orElse(null);

        if (userFounded != null) {
            UserDto userDto = modelMapper.map(userFounded, UserDto.class);
            userDto.setDateOfBirth(userHelper.formDate(userFounded));

            Lesson lessonToSave = lessonService.findById(lesson.getId());
            lessonToSave.setUserId(userFounded.getId());
            lessonService.save(lessonToSave);

            List<TeacherLanguage> teacherLanguages = teacherLanguageService.findAllByLanguageId(lesson.getLanguageId());
            List<Long> list = new ArrayList<>();
            for (TeacherLanguage element : teacherLanguages
            ) {
                list.add(element.getTeacherId());
            }
            List<Lesson> lessons = lessonService.findAllByTeacherIds(list);
            List<Lesson> usersLessons = lessonService.findAllByUserId(userFounded.getId());
            lessons.removeIf(element -> element.getUserId() != null);
            for (Lesson element : usersLessons
            ) {
                lessons.removeIf(elem -> elem.getDay() == element.getDay() && elem.getTime() == element.getTime());
            }

            List<LessonTemplate> lessonList = new ArrayList<>();
            for (Lesson element : lessons
            ) {
                Teacher teacherTemp = teacherService.findById(element.getTeacherId());
                String teacherName = teacherTemp.getName() + " " + teacherTemp.getSurname();
                String date = userHelper.convertIntIntoDate(element.getDay(), element.getTime());
                lessonList.add(new LessonTemplate(element.getId(), lesson.getLanguageId(), element.getTeacherId(),
                        teacherName, date));
            }

            model.addAttribute("user", userDto);
            model.addAttribute("lessons", lessonList);
            model.addAttribute("languageId", lesson.getLanguageId());
            model.addAttribute("token", request.getToken());
            return "user/choosingLessonPage";
        } else {
            model.addAttribute("errorText", "Помилка авторизації!");
            return "authorisation/authorisation";
        }
    }

}
