package com.example.LinguaSphere.service;

import com.example.LinguaSphere.entity.Language;

import java.util.List;

public interface LanguageService {
    List<Language> findAll();
    Language findByName(String name);
    Language findById(Long id);
    void save(Language language);
}
