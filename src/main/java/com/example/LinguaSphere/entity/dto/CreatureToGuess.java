package com.example.LinguaSphere.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatureToGuess {
    Long id;
    String name;
    List<String> hints;
    String language;
    int hintsLeft;
}
