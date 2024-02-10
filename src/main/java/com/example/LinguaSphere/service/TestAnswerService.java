package com.example.LinguaSphere.service;

import com.example.LinguaSphere.entity.TestAnswer;

import java.util.List;

public interface TestAnswerService {
    void save(TestAnswer testAnswer);
    TestAnswer findById(Long id);
    List<TestAnswer> findAllByIds(List<Long> ids);
    void deleteById(Long id);
    void deleteByIds(List<Long> ids);
}
