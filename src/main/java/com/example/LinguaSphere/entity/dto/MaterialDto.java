package com.example.LinguaSphere.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialDto {

    private Long Id;
    private Long teacherId;
    private Long languageId;
    private String language;
    private String text;
    private List<String> links;
    private MultipartFile fileImg;
    private MultipartFile file;

}
