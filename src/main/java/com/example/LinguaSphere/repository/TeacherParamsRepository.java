package com.example.LinguaSphere.repository;

import com.example.LinguaSphere.entity.TeacherParams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherParamsRepository extends JpaRepository<TeacherParams, Long> {
    TeacherParams findByTeacherIdAndLanguageId(Long teacherId, Long languageId);
    List<TeacherParams> findAllByLanguageId(Long languageId);
}
