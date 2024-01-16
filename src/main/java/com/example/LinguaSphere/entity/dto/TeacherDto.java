package com.example.LinguaSphere.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDto {

    private String name;
    private String surname;
    private String email;
    private String phone;
    private String[] contacts;
    private String password;

}
