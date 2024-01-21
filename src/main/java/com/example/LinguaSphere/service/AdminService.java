package com.example.LinguaSphere.service;

import com.example.LinguaSphere.entity.Admin;
import com.example.LinguaSphere.entity.User;

import java.util.List;

public interface AdminService {
    Object[] validateAdmin(Admin admin);
    Admin findByLogin(String login);
    List<Admin> findAll();
    void save(Admin admin);
    void deleteById(Long id);
}
