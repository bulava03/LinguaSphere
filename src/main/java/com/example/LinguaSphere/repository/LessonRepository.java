package com.example.LinguaSphere.repository;

import com.example.LinguaSphere.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAllByTeacherId(Long teacherId);
    List<Lesson> findAllByUserId(Long userId);
    List<Lesson> findAllByLanguageId(Long languageId);
    void deleteByTeacherId(Long teacherId);
    void deleteByUserId(Long userId);
    void deleteByLanguageId(Long languageId);
}
