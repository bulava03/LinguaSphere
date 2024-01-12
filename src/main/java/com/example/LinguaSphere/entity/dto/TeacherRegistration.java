package com.example.LinguaSphere.entity.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Пароль обов'язковий.")
    @Size(min = 8, message = "Пароль повинен містити від 8 до 20 символів.")
    private String password;

    @NotBlank(message = "Ім'я обов'язкове.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Ім'я може містити тільки літери.")
    @Size(max = 50, message = "Ім'я повинне містити не більше 50 символів.")
    private String name;

    @Size(max = 50, message = "Прізвище повинне містити не більше 50 символів.")
    private String surname;

    @NotBlank(message = "Електронна пошта обов'язкова.")
    @Email(message = "Некоректна адреса електронної пошти.")
    @Size(max = 100, message = "Адреса електронної пошти повинна містити не більше 100 символів.")
    private String email;

    @NotBlank(message = "Номер телефону обов'язковий")
    @Pattern(regexp = "^[\\d\\s()+-]+$", message = "Номер телефону може містити лише цифри, пробіли та символи '+', '-', '(', ')'.")
    @Size(max = 20, message = "Номер телефону повинен містити не більше 20 символів.")
    private String phone;

    private String[] contacts;
    private String[] languages;

}
