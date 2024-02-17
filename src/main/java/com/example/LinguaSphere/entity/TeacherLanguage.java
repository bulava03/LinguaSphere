package com.example.LinguaSphere.entity;

import com.example.LinguaSphere.entity.dto.TeacherLanguageDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Long teacherId;
    private Long languageId;
    private double price;

    public TeacherLanguage(Long teacherId, Long languageId) {
        this.teacherId = teacherId;
        this.languageId = languageId;
    }

}
