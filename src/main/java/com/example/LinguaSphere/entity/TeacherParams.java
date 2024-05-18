package com.example.LinguaSphere.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TeacherParams {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long teacherId;
    private Long languageId;
    private double satisfaction; // Задоволеність учнів
    private int teachingExperience; // Досвід викладання у роках
    private int certificates; // Кількість сертифікатів/дипломів

    public TeacherParams(Long teacherId, Long languageId, double satisfaction, int teachingExperience, int certificates) {
        this.teacherId = teacherId;
        this.languageId = languageId;
        this.satisfaction = satisfaction;
        this.teachingExperience = teachingExperience;
        this.certificates = certificates;
    }

    public TeacherParams(double satisfaction, int teachingExperience, int certificates) {
        this.satisfaction = satisfaction;
        this.teachingExperience = teachingExperience;
        this.certificates = certificates;
    }

}
