package com.example.LinguaSphere.repository;

import com.example.LinguaSphere.entity.TestQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestQuestionRepository extends JpaRepository<TestQuestion, Long> {
    List<TestQuestion> findAllByLanguageId(Long languageId);
}
