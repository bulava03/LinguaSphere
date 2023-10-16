package com.example.LinguaSphere.service;

import com.example.LinguaSphere.entity.User;
import com.example.LinguaSphere.entity.dto.UserForm;

import java.util.Optional;

public interface UserService {

    Object[] validateUser(User user);
    void addUser(User user);
    Optional<User> findByEmail(String email);

}
