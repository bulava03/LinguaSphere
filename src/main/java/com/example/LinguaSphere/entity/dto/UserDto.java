package com.example.LinguaSphere.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String name;
    private String surname;
    private String email;
    private String phone;
    private int day;
    private String month;
    private int year;
    private String dateOfBirth;
    private List<String> contacts;
    private MultipartFile file;
    private Long score;

}
