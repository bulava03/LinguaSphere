package com.example.LinguaSphere.service;

import com.example.LinguaSphere.entity.Payment;

import java.util.List;

public interface PaymentService {
    void save(Payment payment);
    void deleteById(Long id);
    void deleteByUserId(Long id);
    Payment findById(Long id);
    Payment findByUserId(Long userId);
    List<Payment> findAll(Long userId);
}
