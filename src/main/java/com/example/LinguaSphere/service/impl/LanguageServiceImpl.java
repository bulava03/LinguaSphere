package com.example.LinguaSphere.service.impl;

import com.example.LinguaSphere.entity.Language;
import com.example.LinguaSphere.repository.LanguageRepository;
import com.example.LinguaSphere.service.LanguageService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private Validator validator;

    @Override
    public Object[] validateLanguage(Language language) {
        Set<ConstraintViolation<Language>> violations = validator.validate(language);

        if (!violations.isEmpty()) {
            List<String> errorMessages = violations.stream()
                    .map(ConstraintViolation::getMessage).toList();
            return new Object[] { false, errorMessages.stream().findFirst() };
        } else {
            return new Object[] { true, "" };
        }
    }

    @Override
    public List<Language> findAll() {
        return languageRepository.findAll();
    }

    @Override
    public Language findByName(String name) {
        return languageRepository.findByName(name);
    }

    @Override
    public Language findById(Long id) {
        return languageRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Language language) {
        languageRepository.save(language);
    }

    @Override
    public void deleteById(Long id) {
        languageRepository.deleteById(id);
    }

}
