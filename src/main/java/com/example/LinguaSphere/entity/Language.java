package com.example.LinguaSphere.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Назва обов'язкова")
    @Column(unique = true)
    private String name;
    @NotBlank(message = "Назва обов'язкова")
    @Column(unique = true)
    private String subName;

}
