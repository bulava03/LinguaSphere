package com.example.LinguaSphere.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonTemplate {
    private Long id;
    private Long languageId;
    private Long teacherId;
    private String teacherEmail;
    private String name;
    private int day;
    private int time;
}
