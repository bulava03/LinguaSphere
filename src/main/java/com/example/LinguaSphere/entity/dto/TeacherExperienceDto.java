package com.example.LinguaSphere.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherExperienceDto {

    private Long id;
    private Long teacherId;
    private Long languageId;
    private String language;
    int experience;

}
