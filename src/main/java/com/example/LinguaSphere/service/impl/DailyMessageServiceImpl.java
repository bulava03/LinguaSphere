package com.example.LinguaSphere.service.impl;

import com.example.LinguaSphere.entity.DailyMessage;
import com.example.LinguaSphere.repository.DailyMessageRepository;
import com.example.LinguaSphere.service.DailyMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyMessageServiceImpl implements DailyMessageService {

    @Autowired
    private DailyMessageRepository dailyMessageRepository;

    @Override
    public List<DailyMessage> findAll() {
        return dailyMessageRepository.findAll();
    }

    @Override
    public void save(DailyMessage dailyMessage) {
        dailyMessageRepository.save(dailyMessage);
    }

}
