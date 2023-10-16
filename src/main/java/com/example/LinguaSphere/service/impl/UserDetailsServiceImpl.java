package com.example.LinguaSphere.service.impl;

import com.example.LinguaSphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final com.example.LinguaSphere.entity.User user = userRepository
                .findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Користувача не знайдено"));
        return new User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }

}
