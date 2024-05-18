package com.example.LinguaSphere.repository;

import com.example.LinguaSphere.entity.TeacherGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherGradeRepository extends JpaRepository<TeacherGrade, Long> {
    List<TeacherGrade> findAllByTeacherIdAndLanguageId(Long teacherId, Long languageId);
    List<TeacherGrade> findAllByUserIdAndLanguageId(Long userId, Long languageId);
}
