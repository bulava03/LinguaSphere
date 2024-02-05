package com.example.LinguaSphere.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoForm {

    private String name;
    private String surname;
    private String email;
    private String phone;
    private int day;
    private int month;
    private int year;
    private LocalDateTime dateOfBirth;
    private List<String> contacts;
    private MultipartFile file;
    private Long score;

}
