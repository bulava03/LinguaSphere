package com.example.LinguaSphere.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Логін обов'язковий.")
    @Size(min = 1, message = "Пароль повинен містити від 1 до 20 символів.")
    @Column(unique = true)
    private String login;
    @NotBlank(message = "Пароль обов'язковий.")
    @Size(min = 8, message = "Пароль повинен містити від 8 до 20 символів.")
    private String password;

}
