package com.example.LinguaSphere.repository;

import com.example.LinguaSphere.entity.TeacherLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherLanguageRepository extends JpaRepository<TeacherLanguage, Long> {
    List<TeacherLanguage> findByTeacherId(Long teacherId);
    List<TeacherLanguage> findAllByLanguageId(Long languageId);
}
