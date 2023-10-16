package com.example.LinguaSphere.entity.dto;

import lombok.Data;

@Data
public class UserDto {

    private String name;
    private String surname;
    private String email;
    private String phone;
    private String dateOfBirth;
    private String[] contacts;

}
