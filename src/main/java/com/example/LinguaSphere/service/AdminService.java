package com.example.LinguaSphere.service;

import com.example.LinguaSphere.entity.Admin;

import java.util.List;

public interface AdminService {
    Admin findByLogin(String login);
    List<Admin> findAll();
    void save(Admin admin);
}
