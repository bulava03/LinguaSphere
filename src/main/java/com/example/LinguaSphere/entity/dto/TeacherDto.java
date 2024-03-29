package com.example.LinguaSphere.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDto {

    private String name;
    private String surname;
    private String email;
    private String phone;
    private List<String> contacts;
    private String password;
    private String aboutMe;
    private MultipartFile file;
    private String emailOld;
    private String passwordOld;

}
