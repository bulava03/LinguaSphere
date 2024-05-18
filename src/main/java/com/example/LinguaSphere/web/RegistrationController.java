package com.example.LinguaSphere.web;

import com.example.LinguaSphere.config.security.JwtTokenService;
import com.example.LinguaSphere.entity.*;
import com.example.LinguaSphere.entity.dto.TeacherRegistration;
import com.example.LinguaSphere.entity.dto.UserDto;
import com.example.LinguaSphere.entity.dto.UserForm;
import com.example.LinguaSphere.helper.ConvertHelper;
import com.example.LinguaSphere.service.*;
import com.example.LinguaSphere.service.impl.UserDetailsServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private LanguageService languageService;
    @Autowired
    private TeacherLanguageService teacherLanguageService;
    @Autowired
    private TeacherParamsService teacherParamsService;
    @Autowired
    private TeacherExperienceService teacherExperienceService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtTokenService jwtTokenService;

    @GetMapping("/registrationType")
    public String getRegistrationTypeChoosingPage() {
        return "registration/registrationType";
    }

    @GetMapping("/registrationUser")
    public String getRegistrationUserPage(Model model) {
        UserForm user = new UserForm();
        user.setYear(-1);
        model.addAttribute("user", user);
        return "registration/registrationUser";
    }

    @PostMapping("/registrationUser")
    public String registrationUser(@ModelAttribute("user") User user, Model model) {
        Object[] validation = userService.validateUser(user);
        if (!(boolean) validation[0]) {
            UserForm userForm = modelMapper.map(user, UserForm.class);
            userForm.setDay(user.getDateOfBirth().getDayOfMonth());
            userForm.setMonth(ConvertHelper.monthToString(user.getDateOfBirth().getMonthValue()));
            userForm.setYear(user.getDateOfBirth().getYear());

            model.addAttribute("user", userForm);
            model.addAttribute("errorText", validation[1].toString().replaceAll("Optional\\[|\\]", ""));
            return "registration/registrationUser";
        } else if (userService.findByEmail(user.getEmail()).isPresent()) {
            UserForm userForm = modelMapper.map(user, UserForm.class);
            userForm.setDay(user.getDateOfBirth().getDayOfMonth());
            userForm.setMonth(ConvertHelper.monthToString(user.getDateOfBirth().getMonthValue()));
            userForm.setYear(user.getDateOfBirth().getYear());

            model.addAttribute("user", userForm);
            model.addAttribute("errorText", "Таку електронну пошту вже зайнято!");
            return "registration/registrationUser";
        } else {
            user.setDateOfBirth(LocalDateTime.of(user.getDateOfBirth().getYear(), user.getDateOfBirth().getMonthValue(), user.getDateOfBirth().getDayOfMonth(), 0, 0, 0));
            userService.addUser(user);

            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        user.getEmail(), user.getPassword(), Collections.emptyList()));
            } catch (final BadCredentialsException ex) {
                model.addAttribute("errorText", "Логін або пароль ввидено неправильно!");
                return "authorisation/authorisation";
            }
            final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            String token = jwtTokenService.generateToken(userDetails);

            return "redirect:/user/userPage?token=" + token;
        }
    }

    @GetMapping("/registrationTeacher")
    public String getRegistrationTeacherPage(Model model) {
        model.addAttribute("languages", languageService.findAll());
        return "registration/registrationTeacher";
    }

    @PostMapping("registrationTeacher")
    public String registrationTeacher(TeacherRegistration teacherRegistration, Admin admin, Model model) {
        Teacher teacher = modelMapper.map(teacherRegistration, Teacher.class);

        String[] parts = teacher.getPassword().split(",");
        if (parts.length > 1) {
            admin.setPassword(parts[1]);
            teacher.setPassword(parts[0]);
        }

        Object[] validation = teacherService.validateTeacher(teacher);
        if (!(boolean) validation[0]) {
            model.addAttribute("admin", admin);
            model.addAttribute("languages", languageService.findAll());
            model.addAttribute("errorText", validation[1].toString().replaceAll("Optional\\[|\\]", ""));
            return "registration/registrationTeacher";
        } else if (teacherService.findByEmail(teacher.getEmail()) != null) {
            model.addAttribute("admin", admin);
            model.addAttribute("languages", languageService.findAll());
            model.addAttribute("errorText", "Викладач з такою електронною поштою вже існує!");
            return "registration/registrationTeacher";
        } else {
            teacherService.save(teacher);
            teacher = teacherService.findByEmail(teacher.getEmail());
            List<Language> languageList = languageService.findAll();
            for (Language elem : languageList) {
                TeacherParams teacherParams = new TeacherParams(teacher.getId(), elem.getId(), 0, 0, 0);
                teacherParamsService.save(teacherParams);
                TeacherExperience teacherExperience = new TeacherExperience(teacher.getId(), elem.getId());
                teacherExperienceService.save(teacherExperience);
            }
            List<TeacherLanguage> subjects = new ArrayList<>();
            List<String> teachersSubjects = Arrays.stream(teacherRegistration.getLanguages()).toList();
            for (String subject : teachersSubjects
            ) {
                subjects.add(new TeacherLanguage(teacher.getId(), languageService.findByName(subject).getId()));
            }
            teacherLanguageService.saveAll(subjects);

            return "redirect:/teacher/teacherPage?email=" + teacherRegistration.getEmail() + "&password=" + teacherRegistration.getPassword();
        }
    }

}
