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

    @Override
    public void save(TeacherLanguage teacherLanguage) {
        teacherLanguageRepository.save(teacherLanguage);
    }

    @Override
    public void saveAll(List<TeacherLanguage> teacherLanguageList) {
        teacherLanguageRepository.saveAll(teacherLanguageList);
    }

    @Override
    public List<TeacherLanguage> findAllByTeacherId(Long teacherId) {
        return teacherLanguageRepository.findByTeacherId(teacherId);
    }

    @Override
    public List<TeacherLanguage> findAllByLanguageId(Long id) {
        return teacherLanguageRepository.findAllByLanguageId(id);
    }

}
