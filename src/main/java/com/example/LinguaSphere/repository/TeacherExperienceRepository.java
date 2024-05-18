package com.example.LinguaSphere.repository;

import com.example.LinguaSphere.entity.TeacherExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherExperienceRepository extends JpaRepository<TeacherExperience, Long> {
    List<TeacherExperience> findAllByTeacherId(Long teacherId);
    TeacherExperience findByTeacherIdAndLanguageId(Long teacherId, Long languageId);
}
