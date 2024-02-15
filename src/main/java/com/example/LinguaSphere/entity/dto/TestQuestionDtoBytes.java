package com.example.LinguaSphere.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestQuestionDtoBytes {

    private Long id;
    private String text;
    private String file;
    private List<Long> correctAnswers;
    private int level;
    private Long languageId;
    private boolean isAvailable;

}
