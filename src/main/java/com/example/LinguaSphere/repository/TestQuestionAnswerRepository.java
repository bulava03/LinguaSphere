package com.example.LinguaSphere.repository;

import com.example.LinguaSphere.entity.TestQuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestQuestionAnswerRepository extends JpaRepository<TestQuestionAnswer, Long> {
    List<TestQuestionAnswer> findAllByQuestionId(Long questionId);
    TestQuestionAnswer findByAnswerId(Long answerId);
}
