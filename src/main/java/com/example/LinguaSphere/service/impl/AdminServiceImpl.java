package com.example.LinguaSphere.service.impl;

import com.example.LinguaSphere.entity.Admin;
import com.example.LinguaSphere.repository.AdminRepository;
import com.example.LinguaSphere.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin findByLogin(String login) {
        return adminRepository.findByLogin(login);
    }

    @Override
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    @Override
    public void save(Admin admin) {
        adminRepository.save(admin);
    }

}
