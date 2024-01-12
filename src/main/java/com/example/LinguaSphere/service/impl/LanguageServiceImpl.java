package com.example.LinguaSphere.service.impl;

import com.example.LinguaSphere.entity.Language;
import com.example.LinguaSphere.repository.LanguageRepository;
import com.example.LinguaSphere.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

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

}
