package com.example.LinguaSphere.repository;

import com.example.LinguaSphere.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByUserId(Long userId);
    void deleteByUserId(Long userId);
}
