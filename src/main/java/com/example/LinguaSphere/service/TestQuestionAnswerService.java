package com.example.LinguaSphere.service;

import com.example.LinguaSphere.entity.TestQuestionAnswer;

import java.util.List;

public interface TestQuestionAnswerService {
    void save(TestQuestionAnswer testQuestionAnswer);
    TestQuestionAnswer findById(Long id);
    List<TestQuestionAnswer> findAllByQuestionId(Long questionId);
    TestQuestionAnswer findByAnswerId(Long answerId);
    void deleteById(Long id);
    void deleteByIds(List<Long> ids);
}
