package com.example.LinguaSphere.web;

import com.example.LinguaSphere.entity.Lesson;
import com.example.LinguaSphere.entity.Teacher;
import com.example.LinguaSphere.entity.dto.TeacherDto;
import com.example.LinguaSphere.helper.LessonHelper;
import com.example.LinguaSphere.service.LessonService;
import com.example.LinguaSphere.service.TeacherService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private ModelMapper modelMapper;

    private final LessonHelper lessonHelper = new LessonHelper();

    @GetMapping("/teacherPage")
    public String teacherPage(Teacher teacher, Model model) {
        Teacher teacherFounded = teacherService.findByEmail(teacher.getEmail());
        if (teacherFounded != null) {
            TeacherDto teacherDto = modelMapper.map(teacherFounded, TeacherDto.class);
            model.addAttribute("teacher", teacherDto);
            return "teacher/teacherPage";
        } else {
            model.addAttribute("errorText", "Такого користувача не існує!");
            return "authorisation/authorisation";
        }
    }

    @GetMapping("/teacherSchedule")
    public String teacherSchedule(Teacher teacher, Model model) {
        Teacher teacherFounded = teacherService.findByEmail(teacher.getEmail());
        if (teacherFounded == null) {
            model.addAttribute("errorText", "Такого користувача не існує!");
            return "authorisation/authorisation";
        }

        TeacherDto teacherDto = modelMapper.map(teacherFounded, TeacherDto.class);
        model.addAttribute("teacher", teacherDto);

        int[][] lessons = new int[7][16];
        List<Lesson> list = lessonService.findAllByTeacherId(teacherFounded.getId());
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 16; j++) {
                lessons[i][j] = lessonHelper.findLessonByDate(i, j, list);
            }
        }

        model.addAttribute("lessons", lessons);
        model.addAttribute("teacher", teacherDto);
        return "teacher/teacherSchedule";
    }

    @PostMapping("/submitCellUsed")
    public String submitCellUsed(Teacher teacher, Lesson lesson, Model model) {
        Teacher teacherFounded = teacherService.findByEmail(teacher.getEmail());
        if (teacherFounded == null) {
            model.addAttribute("errorText", "Такого користувача не існує!");
            return "authorisation/authorisation";
        }

        List<Lesson> list = lessonService.findAllByTeacherId(teacherFounded.getId());
        for (Lesson element : list
             ) {
            if (element.getDay() == lesson.getDay() && element.getTime() == lesson.getTime()) {
                lessonService.deleteById(element.getId());
            }
        }

        TeacherDto teacherDto = modelMapper.map(teacherFounded, TeacherDto.class);
        int[][] lessons = new int[7][16];
        list = lessonService.findAllByTeacherId(teacherFounded.getId());
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 16; j++) {
                lessons[i][j] = lessonHelper.findLessonByDate(i, j, list);
            }
        }

        model.addAttribute("lessons", lessons);
        model.addAttribute("teacher", teacherDto);
        return "teacher/teacherSchedule";
    }

    @PostMapping("/submitCellFree")
    public String submitCellFree(Teacher teacher, Lesson lesson, Model model) {
        Teacher teacherFounded = teacherService.findByEmail(teacher.getEmail());
        if (teacherFounded == null) {
            model.addAttribute("errorText", "Такого користувача не існує!");
            return "authorisation/authorisation";
        }

        lesson.setTeacherId(teacherFounded.getId());
        lessonService.save(lesson);

        TeacherDto teacherDto = modelMapper.map(teacherFounded, TeacherDto.class);
        int[][] lessons = new int[7][16];
        List<Lesson> list = lessonService.findAllByTeacherId(teacherFounded.getId());
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 16; j++) {
                lessons[i][j] = lessonHelper.findLessonByDate(i, j, list);
            }
        }

        model.addAttribute("lessons", lessons);
        model.addAttribute("teacher", teacherDto);
        return "teacher/teacherSchedule";
    }

}
