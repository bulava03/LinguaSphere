package com.example.LinguaSphere.service;

import com.example.LinguaSphere.entity.Lesson;
import com.example.LinguaSphere.entity.Teacher;
import com.example.LinguaSphere.entity.User;
import com.example.LinguaSphere.entity.dto.LessonTemplate;

import java.util.List;

public interface LessonService {
    List<Lesson> findAll();
    Lesson findById(Long id);
    List<Lesson> findAllByTeacherId(Long teacherId);
    List<Lesson> findAllByUserId(Long userId);
    List<Lesson> findAllByLanguageId(Long languageId);
    List<Lesson> findAllByTeacherIds(List<Long> list);
    void save (Lesson lesson);
    void deleteById(Long id);
    void deleteByTeacherId(Long teacherId);
    void deleteByUserId(Long userId);
    void deleteByLanguageId(Long languageId);
    void deleteAll(List<Lesson> list);
    void setLessonForUser(Long userId, Teacher teacher, LessonTemplate lessonTemplate);
}
