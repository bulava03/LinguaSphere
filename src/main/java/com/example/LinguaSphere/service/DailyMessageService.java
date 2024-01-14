package com.example.LinguaSphere.service;

import com.example.LinguaSphere.entity.DailyMessage;

import java.util.List;

public interface DailyMessageService {
    List<DailyMessage> findAll();
    void save(DailyMessage dailyMessage);
}
