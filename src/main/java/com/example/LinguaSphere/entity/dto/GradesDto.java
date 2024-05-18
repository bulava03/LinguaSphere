package com.example.LinguaSphere.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradesDto {

    private Long id;
    private String name;
    private String surname;
    private String file;
    private Long languageId;
    private String language;
    private Long teacherId;
    private int grade;

}
