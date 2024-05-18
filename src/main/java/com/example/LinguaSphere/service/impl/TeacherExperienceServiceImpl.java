package com.example.LinguaSphere.service.impl;

import com.example.LinguaSphere.entity.TeacherExperience;
import com.example.LinguaSphere.repository.TeacherExperienceRepository;
import com.example.LinguaSphere.service.TeacherExperienceService;
import com.example.LinguaSphere.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherExperienceServiceImpl implements TeacherExperienceService {

    @Autowired
    private TeacherExperienceRepository teacherExperienceRepository;
    @Autowired
    private TeacherService teacherService;

    @Override
    public TeacherExperience findById(Long id) {
        return teacherExperienceRepository.findById(id).orElse(null);
    }

    @Override
    public void save(TeacherExperience teacherExperience) {
        teacherExperienceRepository.save(teacherExperience);
    }

    @Override
    public void deleteById(Long id) {
        teacherExperienceRepository.deleteById(id);
    }

    @Override
    public List<TeacherExperience> findAllByTeacherId(Long teacherId) {
        return teacherExperienceRepository.findAllByTeacherId(teacherId);
    }

    @Override
    public List<TeacherExperience> findAll() {
        return teacherExperienceRepository.findAll();
    }

    @Override
    public TeacherExperience findByTeacherIdAndLanguageId(Long teacherId, Long languageId) {
        return teacherExperienceRepository.findByTeacherIdAndLanguageId(teacherId, languageId);
    }

}
