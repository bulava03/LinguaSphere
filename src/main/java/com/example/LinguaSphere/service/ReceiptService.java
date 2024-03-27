package com.example.LinguaSphere.service;

import com.example.LinguaSphere.entity.Receipt;

import java.util.List;

public interface ReceiptService {
    void save(Receipt receipt);
    Receipt findById(Long id);
    List<Receipt> findAllByUserId(Long userId);
    void deleteById(Long id);
    void deleteAllByUserId(Long userId);
}
