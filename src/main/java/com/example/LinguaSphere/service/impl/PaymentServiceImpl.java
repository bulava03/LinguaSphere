package com.example.LinguaSphere.service.impl;

import com.example.LinguaSphere.entity.Payment;
import com.example.LinguaSphere.repository.PaymentRepository;
import com.example.LinguaSphere.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public void save(Payment payment) {
        paymentRepository.save(payment);
    }

    @Override
    public void deleteById(Long id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public void deleteByUserId(Long userId) {
        paymentRepository.deleteByUserId(userId);
    }

    @Override
    public Payment findById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }

    @Override
    public Payment findByUserId(Long userId) {
        return paymentRepository.findByUserId(userId);
    }

    @Override
    public List<Payment> findAll(Long userId) {
        return paymentRepository.findAll();
    }

}
