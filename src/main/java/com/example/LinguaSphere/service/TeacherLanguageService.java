package com.example.LinguaSphere.service;

import com.example.LinguaSphere.entity.TeacherLanguage;

import java.util.List;

public interface TeacherLanguageService {
    void save(TeacherLanguage teacherLanguage);
    void saveAll(List<TeacherLanguage> teacherLanguageList);
    List<TeacherLanguage> findAllByTeacherId(Long teacherId);
    List<TeacherLanguage> findAllByLanguageId(Long id);
}
