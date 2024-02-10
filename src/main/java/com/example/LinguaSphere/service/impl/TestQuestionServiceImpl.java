package com.example.LinguaSphere.service.impl;

import com.example.LinguaSphere.entity.TestQuestion;
import com.example.LinguaSphere.repository.TestQuestionRepository;
import com.example.LinguaSphere.service.TestQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestQuestionServiceImpl implements TestQuestionService {

    @Autowired
    private TestQuestionRepository testQuestionRepository;

    @Override
    public void save(TestQuestion testQuestion) {
        testQuestionRepository.save(testQuestion);
    }

    @Override
    public TestQuestion findById(Long id) {
        return testQuestionRepository.findById(id).orElse(null);
    }

    @Override
    public List<TestQuestion> findAllByLanguageId(Long languageId) {
        return testQuestionRepository.findAllByLanguageId(languageId);
    }

    @Override
    public void deleteById(Long id) {
        testQuestionRepository.deleteById(id);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        testQuestionRepository.deleteAllById(ids);
    }

}
