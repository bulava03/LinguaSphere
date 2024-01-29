package com.example.LinguaSphere.service;

import com.example.LinguaSphere.entity.*;
import com.example.LinguaSphere.entity.dto.CreatureToGuess;
import com.example.LinguaSphere.entity.dto.MaterialDtoBytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface LanguageService {
    Object[] validateLanguage(Language language);
    List<Language> findAll();
    Language findByName(String name);
    Language findById(Long id);
    void save(Language language);
    void deleteById(Long id);
    List<Language> getLanguagesFromUserLessons(List<Lesson> userLessons);
    CreatureToGuess getNewCreatureToGuess(Creature creature, CreatureToGuess creatureToGuess);
    List<MaterialDtoBytes> getMaterialDtoBytesFromList(List<Material> materials) throws IOException;
}
