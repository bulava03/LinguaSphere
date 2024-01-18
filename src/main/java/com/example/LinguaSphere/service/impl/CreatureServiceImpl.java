package com.example.LinguaSphere.service.impl;

import com.example.LinguaSphere.entity.Creature;
import com.example.LinguaSphere.repository.CreatureRepository;
import com.example.LinguaSphere.service.CreatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreatureServiceImpl implements CreatureService {

    @Autowired
    private CreatureRepository creatureRepository;

    @Override
    public Creature findById(Long id) {
        return creatureRepository.findById(id).orElse(null);
    }

    @Override
    public List<Creature> findAll() {
        return creatureRepository.findAll();
    }

    @Override
    public List<Creature> findAllByLanguageId(Long languageId) {
        return creatureRepository.findAllByLanguageId(languageId);
    }

    @Override
    public void save(Creature creature) {
        creatureRepository.save(creature);
    }

    @Override
    public void deleteById(Long id) {
        creatureRepository.deleteById(id);
    }

}
