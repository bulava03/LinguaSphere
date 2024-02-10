package com.example.LinguaSphere.service.impl;

import com.example.LinguaSphere.entity.TestQuestionAnswer;
import com.example.LinguaSphere.repository.TestQuestionAnswerRepository;
import com.example.LinguaSphere.service.TestQuestionAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestQuestionAnswerServiceImpl implements TestQuestionAnswerService {

    @Autowired
    private TestQuestionAnswerRepository testQuestionAnswerRepository;

    @Override
    public void save(TestQuestionAnswer testQuestionAnswer) {
        testQuestionAnswerRepository.save(testQuestionAnswer);
    }

    @Override
    public TestQuestionAnswer findById(Long id) {
        return testQuestionAnswerRepository.findById(id).orElse(null);
    }

    @Override
    public List<TestQuestionAnswer> findAllByQuestionId(Long questionId) {
        return testQuestionAnswerRepository.findAllByQuestionId(questionId);
    }

    @Override
    public TestQuestionAnswer findByAnswerId(Long answerId) {
        return testQuestionAnswerRepository.findByAnswerId(answerId);
    }

    @Override
    public void deleteById(Long id) {
        testQuestionAnswerRepository.deleteById(id);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        testQuestionAnswerRepository.deleteAllById(ids);
    }

}
