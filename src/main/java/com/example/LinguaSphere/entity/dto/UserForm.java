package com.example.LinguaSphere.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserForm {
    private String password;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private LocalDateTime dateOfBirth;
    private String[] contacts;
    private int day;
    private String month;
    private int year;
}
