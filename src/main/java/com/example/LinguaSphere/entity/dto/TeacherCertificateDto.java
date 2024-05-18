package com.example.LinguaSphere.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherCertificateDto {

    private Long id;
    private Long teacherId;
    private Long languageId;
    private String language;
    private String file;
    private String fileType;

}
