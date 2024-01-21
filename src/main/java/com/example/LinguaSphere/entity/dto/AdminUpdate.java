package com.example.LinguaSphere.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUpdate {

    private Long id;
    private String login;
    private String password;
    private String oldLogin;
    private String oldPassword;

}
