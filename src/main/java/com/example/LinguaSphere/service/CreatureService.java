package com.example.LinguaSphere.service;

import com.example.LinguaSphere.entity.Creature;

import java.util.List;

public interface CreatureService {
    Creature findById(Long id);
    List<Creature> findAll();
    List<Creature> findAllByLanguageId(Long languageId);
    void save(Creature creature);
    void deleteById(Long id);
}
