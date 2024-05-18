package com.example.LinguaSphere.service.impl;

import com.example.LinguaSphere.entity.TeacherCertificate;
import com.example.LinguaSphere.repository.TeacherCertificateRepository;
import com.example.LinguaSphere.service.TeacherCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherCertificateServiceImpl implements TeacherCertificateService {

    @Autowired
    private TeacherCertificateRepository teacherCertificateRepository;

    @Override
    public void save(TeacherCertificate teacherCertificate) {
        teacherCertificateRepository.save(teacherCertificate);
    }

    @Override
    public void deleteById(Long id) {
        teacherCertificateRepository.deleteById(id);
    }

    @Override
    public TeacherCertificate findById(Long id) {
        return teacherCertificateRepository.findById(id).orElse(null);
    }

    @Override
    public List<TeacherCertificate> findAll() {
        return teacherCertificateRepository.findAll();
    }

    @Override
    public List<TeacherCertificate> findAllByTeacherId(Long teacherId) {
        return teacherCertificateRepository.findAllByTeacherId(teacherId);
    }

    @Override
    public List<TeacherCertificate> findAllByTeacherIdAndLanguageId(Long teacherId, Long languageId) {
        return teacherCertificateRepository.findAllByTeacherIdAndLanguageId(teacherId, languageId);
    }

}
