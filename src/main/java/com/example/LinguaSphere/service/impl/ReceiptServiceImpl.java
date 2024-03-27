package com.example.LinguaSphere.service.impl;

import com.example.LinguaSphere.entity.Receipt;
import com.example.LinguaSphere.repository.ReceiptRepository;
import com.example.LinguaSphere.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    @Autowired
    private ReceiptRepository receiptRepository;

    @Override
    public void save(Receipt receipt) {
        receiptRepository.save(receipt);
    }

    @Override
    public Receipt findById(Long id) {
        return receiptRepository.findById(id).orElse(null);
    }

    @Override
    public List<Receipt> findAllByUserId(Long userId) {
        return receiptRepository.findAllByUserId(userId);
    }

    @Override
    public void deleteById(Long id) {
        receiptRepository.deleteById(id);
    }

    @Override
    public void deleteAllByUserId(Long userId) {
        receiptRepository.deleteAllByUserId(userId);
    }

}
