package com.example.LinguaSphere.service;

import com.example.LinguaSphere.entity.TeacherGrade;

import java.util.List;

public interface TeacherGradeService {
    TeacherGrade findById(Long id);
    List<TeacherGrade> findAllByTeacherIdAndLanguageId(Long teacherId, Long languageId);
    List<TeacherGrade> findAllByUserIdAndLanguageId(Long userId, Long languageId);
    TeacherGrade findByTeacherIdAndLanguageIdAndUserId(Long teacherId, Long languageId, Long userId);
    void save(TeacherGrade teacherGrade);
    void deleteById(Long id);
}
