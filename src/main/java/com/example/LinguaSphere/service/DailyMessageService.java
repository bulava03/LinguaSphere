package com.example.LinguaSphere.service;

import com.example.LinguaSphere.entity.DailyMessage;

import java.util.List;

public interface DailyMessageService {
    List<DailyMessage> findAll();
    DailyMessage findById(Long id);
    void save(DailyMessage dailyMessage);
    void deleteById(Long id);
}
