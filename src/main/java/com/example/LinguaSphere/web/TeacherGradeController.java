package com.example.LinguaSphere.web;

import com.example.LinguaSphere.entity.*;
import com.example.LinguaSphere.entity.dto.GradesDto;
import com.example.LinguaSphere.entity.dto.RequestDto;
import com.example.LinguaSphere.entity.dto.TeacherDtoBytes;
import com.example.LinguaSphere.entity.dto.UserDtoBytes;
import com.example.LinguaSphere.helper.UserHelper;
import com.example.LinguaSphere.service.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/grade")
public class TeacherGradeController {

    @Autowired
    private UserService userService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private LanguageService languageService;
    @Autowired
    private TeacherParamsService teacherParamsService;
    @Autowired
    private TeacherGradeService teacherGradeService;

    @Autowired
    private ModelMapper modelMapper;
    private final UserHelper userHelper = new UserHelper();

    @GetMapping("/getGradesList")
    public String getGradesList(RequestDto request, Model model) {
        Object[] authResult = userService.authenticateUser(request);
        if (!(boolean) authResult[0]) {
            return "authorisation/authorisation";
        }
        User userFounded = userService.findByEmail((String) authResult[1]).orElse(null);
        UserDtoBytes userDto = userHelper.getUserDtoBytes(userFounded);

        List<Lesson> lessons = findUsersLessonsDistinct(userFounded.getId());

        List<GradesDto> gradesDtoList = new ArrayList<>();
        List<TeacherDtoBytes> teacherDtoBytesList = findTeachers(lessons);
        List<Language> languages = findLanguages(lessons);
        List<TeacherGrade> grades = findGrades(lessons);
        for (TeacherDtoBytes teacherDtoBytes : teacherDtoBytesList) {
            gradesDtoList.add(modelMapper.map(teacherDtoBytes, GradesDto.class));
        }
        int temp = 0;
        for (GradesDto gradesDto : gradesDtoList) {
            gradesDto.setLanguageId(languages.get(temp).getId());
            gradesDto.setLanguage(languages.get(temp).getName());
            gradesDto.setGrade(grades.get(temp).getGrade());
            gradesDto.setId(grades.get(temp).getId());
        }

        model.addAttribute("grades", gradesDtoList);
        model.addAttribute("user", userDto);
        model.addAttribute("token", request.getToken());
        return "user/gradesListPage";
    }

    @GetMapping("/getGradingPage")
    public String getGradingPage(RequestDto request, Long id, Model model) {
        Object[] authResult = userService.authenticateUser(request);
        if (!(boolean) authResult[0]) {
            return "authorisation/authorisation";
        }
        User userFounded = userService.findByEmail((String) authResult[1]).orElse(null);
        UserDtoBytes userDto = userHelper.getUserDtoBytes(userFounded);

        model.addAttribute("gradeId", id);
        model.addAttribute("user", userDto);
        model.addAttribute("token", request.getToken());
        return "user/gradingPage";
    }

    @PostMapping("/setGrade")
    public String getGrade(RequestDto request, Long id, int grade, Model model) {
        Object[] authResult = userService.authenticateUser(request);
        if (!(boolean) authResult[0]) {
            return "authorisation/authorisation";
        }
        User userFounded = userService.findByEmail((String) authResult[1]).orElse(null);
        UserDtoBytes userDto = userHelper.getUserDtoBytes(userFounded);

        TeacherGrade teacherGrade = teacherGradeService.findById(id);
        teacherGrade.setGrade(grade);
        teacherGradeService.save(teacherGrade);

        List<TeacherGrade> grades = teacherGradeService.findAllByTeacherIdAndLanguageId(teacherGrade.getTeacherId(), teacherGrade.getLanguageId());
        double averageGrade = 0;
        for (TeacherGrade element : grades) {
            averageGrade = averageGrade + element.getGrade();
        }
        averageGrade = averageGrade / grades.size();
        TeacherParams teacherParams = teacherParamsService.findByTeacherIdAndLanguageId(teacherGrade.getTeacherId(), teacherGrade.getLanguageId());
        teacherParams.setSatisfaction(averageGrade);
        teacherParamsService.save(teacherParams);


        model.addAttribute("user", userDto);
        model.addAttribute("token", request.getToken());
        return "redirect:/grade/getGradesList?token=" + request.getToken();
    }


    private List<Lesson> findUsersLessonsDistinct(Long userId) {
        List<Lesson> lessons = lessonService.findAllByUserId(userId);
        Map<String, Lesson> distinctLessonsMap = lessons.stream()
                .collect(Collectors.toMap(
                        lesson -> lesson.getTeacherId() + "-" + lesson.getLanguageId(),
                        lesson -> lesson,
                        (existing, replacement) -> existing
                ));

        return distinctLessonsMap.values().stream().toList();
    }

    private List<TeacherDtoBytes> findTeachers(List<Lesson> lessons) {
        List<TeacherDtoBytes> teacherDtoBytesList = new ArrayList<>();

        for (Lesson lesson : lessons) {
            Long teacherId = lesson.getTeacherId();
            Teacher teacher = teacherService.findById(teacherId);
            TeacherDtoBytes teacherDtoBytes = modelMapper.map(teacher, TeacherDtoBytes.class);
            teacherDtoBytes.setFile(Base64.encodeBase64String(teacher.getImage()));
            teacherDtoBytesList.add(teacherDtoBytes);
        }

        return teacherDtoBytesList;
    }

    private List<Language> findLanguages(List<Lesson> lessons) {
        List<Language> languages = new ArrayList<>();

        for (Lesson lesson : lessons) {
            Long languageId = lesson.getLanguageId();
            Language language = languageService.findById(languageId);
            languages.add(language);
        }

        return languages;
    }

    private List<TeacherGrade> findGrades(List<Lesson> lessons) {
        List<TeacherGrade> grades = new ArrayList<>();

        for (Lesson lesson : lessons) {
            TeacherGrade grade = teacherGradeService.findByTeacherIdAndLanguageIdAndUserId(
                    lesson.getTeacherId(), lesson.getLanguageId(), lesson.getUserId());
            grades.add(grade);
        }

        return grades;
    }

}
