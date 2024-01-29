package com.example.LinguaSphere.service.impl;

import com.example.LinguaSphere.entity.Creature;
import com.example.LinguaSphere.entity.Language;
import com.example.LinguaSphere.entity.Lesson;
import com.example.LinguaSphere.entity.Material;
import com.example.LinguaSphere.entity.dto.CreatureToGuess;
import com.example.LinguaSphere.entity.dto.MaterialDtoBytes;
import com.example.LinguaSphere.helper.UserHelper;
import com.example.LinguaSphere.repository.LanguageRepository;
import com.example.LinguaSphere.service.LanguageService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.apache.tika.Tika;
import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Validator validator;

    private final UserHelper userHelper = new UserHelper();

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

    @Override
    public List<Language> getLanguagesFromUserLessons(List<Lesson> userLessons) {
        List<Long> languagesIds = userHelper.getLanguageIdsFromLessons(userLessons);
        List<Language> languages = new ArrayList<>();
        for (Long languageId : languagesIds
        ) {
            languages.add(findById(languageId));
        }
        return languages;
    }

    @Override
    public CreatureToGuess getNewCreatureToGuess(Creature creature, CreatureToGuess creatureToGuess) {
        CreatureToGuess newCreatureToGuess = modelMapper.map(creature, CreatureToGuess.class);
        newCreatureToGuess.setLanguage(findById(creature.getLanguageId()).getName());
        newCreatureToGuess.setHintsLeft(creatureToGuess.getHintsLeft() - 1);
        int hintsSize = creature.getHints().size();
        int lastHintIndex = hintsSize - creatureToGuess.getHintsLeft();
        List<String> allHints = creature.getHints();
        List<String> newHints = new ArrayList<>();
        for (int i = 0; i <= lastHintIndex; i++) {
            newHints.add(allHints.get(i));
        }
        newCreatureToGuess.setHints(newHints);
        return newCreatureToGuess;
    }

    @Override
    public List<MaterialDtoBytes> getMaterialDtoBytesFromList(List<Material> materials) throws IOException {
        List<MaterialDtoBytes> materialsDtoList = new ArrayList<>();

        for (Material material : materials
        ) {
            MaterialDtoBytes dto = modelMapper.map(material, MaterialDtoBytes.class);
            dto.setLanguage(findById(material.getLanguageId()).getName());
            dto.setFileImg(Base64.encodeBase64String(material.getImage()));
            dto.setFile(Base64.encodeBase64String(material.getStandardFile()));

            byte[] decodedBytes = material.getStandardFile();
            Path tempFile = Files.createTempFile("tempFile", null);
            try (InputStream is = new ByteArrayInputStream(decodedBytes)) {
                Files.copy(is, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }
            Tika tika = new Tika();
            dto.setFileType(tika.detect(tempFile));

            materialsDtoList.add(dto);
        }

        return materialsDtoList;
    }

}
