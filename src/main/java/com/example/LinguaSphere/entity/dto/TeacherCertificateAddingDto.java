package com.example.LinguaSphere.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherCertificateAddingDto {

    private Long id;
    private Long teacherId;
    private Long languageId;
    private byte[] file;
    private MultipartFile fileToAdd;

}
