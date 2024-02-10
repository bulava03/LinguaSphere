package com.example.LinguaSphere.service;

import com.example.LinguaSphere.entity.TestQuestion;

import java.util.List;

public interface TestQuestionService {
    void save(TestQuestion testQuestion);
    TestQuestion findById(Long id);
    List<TestQuestion> findAllByLanguageId(Long languageId);
    void deleteById(Long id);
    void deleteByIds(List<Long> ids);
}
