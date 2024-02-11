package com.example.LinguaSphere.web.rest;

import com.example.LinguaSphere.entity.Teacher;
import com.example.LinguaSphere.entity.User;
import com.example.LinguaSphere.entity.dto.LessonTemplate;
import com.example.LinguaSphere.entity.dto.RequestDto;
import com.example.LinguaSphere.service.LessonService;
import com.example.LinguaSphere.service.TeacherService;
import com.example.LinguaSphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lesson")
public class LessonController {

    @Autowired
    private LessonService lessonService;
    @Autowired
    private UserService userService;
    @Autowired
    private TeacherService teacherService;


    @PostMapping("/setLesson")
    public void setLesson(String token, LessonTemplate lesson) {
        RequestDto request = new RequestDto(token);
        Object[] authResult = userService.authenticateUser(request);
        if ((boolean) authResult[0]) {
            User userFounded = userService.findByEmail((String) authResult[1]).orElse(null);
            Teacher teacherFounded = teacherService.findByEmail(lesson.getTeacherEmail());
            lessonService.setLessonForUser(userFounded.getId(), teacherFounded, lesson);
        }
    }

    @PostMapping("/removeLesson")
    public void removeLesson(String token, LessonTemplate lesson) {
        RequestDto request = new RequestDto(token);
        Object[] authResult = userService.authenticateUser(request);
        if ((boolean) authResult[0]) {
            Teacher teacherFounded = teacherService.findByEmail(lesson.getTeacherEmail());
            lessonService.setLessonForUser(null, teacherFounded, lesson);
        }
    }

}
