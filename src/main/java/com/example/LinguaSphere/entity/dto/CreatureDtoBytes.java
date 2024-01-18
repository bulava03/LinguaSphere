package com.example.LinguaSphere.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatureDtoBytes {

    private Long id;
    private String language;
    private Long languageId;
    private String name;
    private List<String> hints;
    private String file;

}
