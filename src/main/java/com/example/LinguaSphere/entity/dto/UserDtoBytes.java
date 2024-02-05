package com.example.LinguaSphere.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoBytes {

    private String password;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String[] contacts;
    private int day;
    private String month;
    private int year;
    private String dateOfBirth;
    private Long score;
    private String file;

}
