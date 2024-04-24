package com.example.LinguaSphere.entity;

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
public class PreferredLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Long teacherId;
    private Long userId;
    private String preferredLink;

    public PreferredLink(Long userId, Long teacherId) {
        this.userId = userId;
        this.teacherId = teacherId;
    }

}
