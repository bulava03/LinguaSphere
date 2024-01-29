package com.example.LinguaSphere.service.impl;

import com.example.LinguaSphere.entity.User;
import com.example.LinguaSphere.repository.UserRepository;
import com.example.LinguaSphere.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Validator validator;

    @Override
    public Object[] validateUser(User user) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            List<String> errorMessages = violations.stream()
                    .map(ConstraintViolation::getMessage).toList();
            return new Object[] { false, errorMessages.stream().findFirst() };
        } else {
            return new Object[] { true, "" };
        }
    }

    @Override
    public void addUser(User user) {
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User defaultScore(User user) {
        if (user.getScore() == null) {
            user.setScore(0L);
            updateUser(user);
        }
        return user;
    }

    @Override
    public void updateUserIfGuessed(User user) {
        user.setLastGuessedCount(user.getLastGuessedCount() + 1);
        user.setScore(user.getScore() + 1);
        if (user.getLastGuessedCount() == 10) {
            user.setLastGuessedCount(0);
            user.setScore(user.getScore() + 10);
        }
        updateUser(user);
    }

}
