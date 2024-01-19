package com.example.LinguaSphere.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyMessageDto {

    private Long id;
    private String language;
    private Long languageId;
    private String text;
    private List<String> links;
    private MultipartFile file;

}
