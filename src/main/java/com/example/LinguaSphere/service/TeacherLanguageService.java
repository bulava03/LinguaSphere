package com.example.LinguaSphere.service;

import com.example.LinguaSphere.entity.TeacherLanguage;
import com.example.LinguaSphere.entity.dto.TeacherLanguageDto;

import java.util.List;

public interface TeacherLanguageService {
    void save(TeacherLanguage teacherLanguage);
    void saveAll(List<TeacherLanguage> teacherLanguageList);
    List<TeacherLanguage> findAllByTeacherId(Long teacherId);
    List<TeacherLanguage> findAllByLanguageId(Long id);
    List<TeacherLanguageDto> getPricesByLanguages(Long teacherId);
    TeacherLanguage findByTeacherIdAndLanguageId(Long teacherId, Long languageId);
}
