package com.example.LinguaSphere.service.impl;

import com.example.LinguaSphere.entity.Admin;
import com.example.LinguaSphere.repository.AdminRepository;
import com.example.LinguaSphere.service.AdminService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private Validator validator;

    @Override
    public Object[] validateAdmin(Admin admin) {
        Set<ConstraintViolation<Admin>> violations = validator.validate(admin);

        if (!violations.isEmpty()) {
            List<String> errorMessages = violations.stream()
                    .map(ConstraintViolation::getMessage).toList();
            return new Object[] { false, errorMessages.stream().findFirst() };
        } else {
            return new Object[] { true, "" };
        }
    }

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

    @Override
    public void deleteById(Long id) {
        adminRepository.deleteById(id);
    }

}
