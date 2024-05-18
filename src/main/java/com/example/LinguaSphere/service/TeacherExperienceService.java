package com.example.LinguaSphere.service;

import com.example.LinguaSphere.entity.TeacherExperience;

import java.util.List;

public interface TeacherExperienceService {
    TeacherExperience findById(Long id);
    void save(TeacherExperience teacherExperience);
    void deleteById(Long id);
    List<TeacherExperience> findAllByTeacherId(Long teacherId);
    List<TeacherExperience> findAll();
    TeacherExperience findByTeacherIdAndLanguageId(Long teacherId, Long languageId);
}
