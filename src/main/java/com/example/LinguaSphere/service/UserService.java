package com.example.LinguaSphere.service;

import com.example.LinguaSphere.entity.User;

import java.util.Optional;

public interface UserService {

    Object[] validateUser(User user);
    void addUser(User user);
    void updateUser(User user);
    Optional<User> findByEmail(String email);
    User findById(Long id);
    User defaultScore(User user);
    void updateUserIfGuessed(User user);
    User setDailyDateAndId(User user);

}
