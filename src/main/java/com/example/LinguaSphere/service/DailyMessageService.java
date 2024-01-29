package com.example.LinguaSphere.service;

import com.example.LinguaSphere.entity.DailyMessage;
import com.example.LinguaSphere.entity.Lesson;

import java.util.List;

public interface DailyMessageService {
    List<DailyMessage> findAll();
    DailyMessage findById(Long id);
    List<DailyMessage> findAllByLanguageId(Long languageId);
    void save(DailyMessage dailyMessage);
    void deleteById(Long id);
    List<DailyMessage> getDailyMessagesFromUserLessons(List<Lesson> userLessons);
}
