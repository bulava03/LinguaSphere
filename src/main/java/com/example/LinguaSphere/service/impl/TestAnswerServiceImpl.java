package com.example.LinguaSphere.service.impl;

import com.example.LinguaSphere.entity.TestAnswer;
import com.example.LinguaSphere.repository.TestAnswerRepository;
import com.example.LinguaSphere.service.TestAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestAnswerServiceImpl implements TestAnswerService {

    @Autowired
    private TestAnswerRepository testAnswerRepository;

    @Override
    public void save(TestAnswer testAnswer) {
        testAnswerRepository.save(testAnswer);
    }

    @Override
    public TestAnswer findById(Long id) {
        return testAnswerRepository.findById(id).orElse(null);
    }

    @Override
    public List<TestAnswer> findAllByIds(List<Long> ids) {
        return testAnswerRepository.findAllById(ids);
    }

    @Override
    public void deleteById(Long id) {
        testAnswerRepository.deleteById(id);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        testAnswerRepository.deleteAllById(ids);
    }

}
