package com.example.LinguaSphere.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyMessageDtoBytes {

    private Long id;
    private String language;
    private Long languageId;
    private String text;
    private String links;
    private String file;

}
