package com.example.LinguaSphere.repository;

import com.example.LinguaSphere.entity.TeacherCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherCertificateRepository extends JpaRepository<TeacherCertificate, Long> {
    List<TeacherCertificate> findAllByTeacherId(Long teacherId);
    List<TeacherCertificate> findAllByTeacherIdAndLanguageId(Long teacherId, Long languageId);
}
