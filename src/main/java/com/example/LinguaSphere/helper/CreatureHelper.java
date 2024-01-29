package com.example.LinguaSphere.helper;

import com.example.LinguaSphere.entity.Creature;
import com.example.LinguaSphere.entity.dto.CreatureDtoBytes;
import com.example.LinguaSphere.entity.dto.CreatureToGuess;
import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class CreatureHelper {

    private final ModelMapper modelMapper = new ModelMapper();

    public CreatureDtoBytes convertCreatureToDtoBytes(Creature creature, String language) {
        creature.setHints(new ArrayList<>());
        CreatureDtoBytes creatureDtoBytes = modelMapper.map(creature, CreatureDtoBytes.class);
        creatureDtoBytes.setFile(Base64.encodeBase64String(creature.getImage()));
        creatureDtoBytes.setLanguage(language);
        return creatureDtoBytes;
    }

    public CreatureToGuess getCreatureIfAnsweredIncorrect(
            Creature creature, CreatureToGuess creatureAnswered, String languageName) {

        CreatureToGuess newCreatureToGuess = modelMapper.map(creature, CreatureToGuess.class);
        newCreatureToGuess.setLanguage(languageName);
        newCreatureToGuess.setHintsLeft(creatureAnswered.getHintsLeft());
        int hintsSize = creature.getHints().size();
        int lastHintIndex = hintsSize - creatureAnswered.getHintsLeft();
        List<String> allHints = creature.getHints();
        List<String> newHints = new ArrayList<>();
        for (int i = 0; i < lastHintIndex; i++) {
            newHints.add(allHints.get(i));
        }
        newCreatureToGuess.setHints(newHints);

        return newCreatureToGuess;

    }

}
