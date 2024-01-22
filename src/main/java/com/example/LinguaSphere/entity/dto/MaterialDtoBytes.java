package com.example.LinguaSphere.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialDtoBytes {

    private Long Id;
    private Long teacherId;
    private Long languageId;
    private String language;
    private String text;
    private List<String> links;
    private String fileImg;
    private String file;
    private String fileType;

}
