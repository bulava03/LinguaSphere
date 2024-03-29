package com.example.LinguaSphere.service.impl;

import com.example.LinguaSphere.entity.DailyMessage;
import com.example.LinguaSphere.entity.Lesson;
import com.example.LinguaSphere.helper.UserHelper;
import com.example.LinguaSphere.repository.DailyMessageRepository;
import com.example.LinguaSphere.service.DailyMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DailyMessageServiceImpl implements DailyMessageService {

    @Autowired
    private DailyMessageRepository dailyMessageRepository;

    private final UserHelper userHelper = new UserHelper();


    @Override
    public List<DailyMessage> findAll() {
        return dailyMessageRepository.findAll();
    }

    @Override
    public DailyMessage findById(Long id) {
        return dailyMessageRepository.findById(id).orElse(null);
    }

    @Override
    public List<DailyMessage> findAllByLanguageId(Long languageId) {
        return dailyMessageRepository.findAllByLanguageId(languageId);
    }

    @Override
    public void save(DailyMessage dailyMessage) {
        dailyMessageRepository.save(dailyMessage);
    }

    @Override
    public void deleteById(Long id) {
        dailyMessageRepository.deleteById(id);
    }

    @Override
    public List<DailyMessage> getDailyMessagesFromUserLessons(List<Lesson> userLessons) {
        List<Long> languagesIds = userHelper.getLanguageIdsFromLessons(userLessons);
        List<DailyMessage> variants = new ArrayList<>();
        for (Long languageId : languagesIds
        ) {
            variants.addAll(findAllByLanguageId(languageId));
        }
        return variants;
    }

}
