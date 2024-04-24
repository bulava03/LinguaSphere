package com.example.LinguaSphere.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreferredLinkDto {

    private Long id;
    private Long userId;
    private Long teacherId;
    private String teacherName;
    private String teacherSurname;
    private String file;
    private String link;

}
