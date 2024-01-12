package com.example.LinguaSphere.service.impl;

import com.example.LinguaSphere.entity.TeacherLanguage;
import com.example.LinguaSphere.repository.TeacherLanguageRepository;
import com.example.LinguaSphere.service.TeacherLanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherLanguageServiceImpl implements TeacherLanguageService {

    @Autowired
    private TeacherLanguageRepository teacherLanguageRepository;

    public void save(TeacherLanguage teacherLanguage) {
        teacherLanguageRepository.save(teacherLanguage);
    }

    public void saveAll(List<TeacherLanguage> teacherLanguageList) {
        teacherLanguageRepository.saveAll(teacherLanguageList);
    }

    public List<TeacherLanguage> findByTeacherId(Long teacherId) {
        return teacherLanguageRepository.findByTeacherId(teacherId);
    }

}
