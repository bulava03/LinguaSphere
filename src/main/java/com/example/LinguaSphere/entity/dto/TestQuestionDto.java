package com.example.LinguaSphere.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestQuestionDto {

    private Long id;
    private String text;
    private MultipartFile file;
    private int correctAnswer;
    private int level;
    private Long languageId;

}
