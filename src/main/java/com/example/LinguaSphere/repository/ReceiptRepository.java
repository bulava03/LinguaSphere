package com.example.LinguaSphere.repository;

import com.example.LinguaSphere.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    List<Receipt> findAllByUserId(Long userId);
    void deleteAllByUserId(Long userId);
}
