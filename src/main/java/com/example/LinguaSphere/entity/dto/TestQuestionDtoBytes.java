package com.example.LinguaSphere.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestQuestionDtoBytes {

    private Long id;
    private String text;
    private String file;
    private int correctAnswer;
    private int level;
    private Long languageId;

}
