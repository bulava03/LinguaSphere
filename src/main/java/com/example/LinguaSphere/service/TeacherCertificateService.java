package com.example.LinguaSphere.service;

import com.example.LinguaSphere.entity.TeacherCertificate;

import java.util.List;

public interface TeacherCertificateService {

    void save(TeacherCertificate teacherCertificate);
    void deleteById(Long id);
    TeacherCertificate findById(Long id);
    List<TeacherCertificate> findAll();
    List<TeacherCertificate> findAllByTeacherId(Long teacherId);
    List<TeacherCertificate> findAllByTeacherIdAndLanguageId(Long teacherId, Long languageId);

}
