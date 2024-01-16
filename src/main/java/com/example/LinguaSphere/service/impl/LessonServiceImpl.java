package com.example.LinguaSphere.service.impl;

import com.example.LinguaSphere.entity.Lesson;
import com.example.LinguaSphere.repository.LessonRepository;
import com.example.LinguaSphere.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Override
    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }

    @Override
    public Lesson findById(Long id) {
        return lessonRepository.findById(id).orElse(null);
    }

    @Override
    public List<Lesson> findAllByTeacherId(Long teacherId) {
        return lessonRepository.findAllByTeacherId(teacherId);
    }

    @Override
    public List<Lesson> findAllByUserId(Long userId) {
        return lessonRepository.findAllByUserId(userId);
    }

    @Override
    public List<Lesson> findAllByLanguageId(Long languageId) {
        return lessonRepository.findAllByLanguageId(languageId);
    }

    @Override
    public List<Lesson> findAllFreeByLanguageId(Long languageId) {
        List<Lesson> list = lessonRepository.findAllByLanguageId(languageId);
        list.removeIf(lesson -> lesson.getUserId() != null);
        return list;
    }

    @Override
    public void save (Lesson lesson) {
        lessonRepository.save(lesson);
    }

    @Override
    public void deleteById(Long id) {
        lessonRepository.deleteById(id);
    }

    @Override
    public void deleteByTeacherId(Long teacherId) {
        lessonRepository.deleteByTeacherId(teacherId);
    }

    @Override
    public void deleteByUserId(Long userId) {
        lessonRepository.deleteByUserId(userId);
    }

    @Override
    public void deleteByLanguageId(Long languageId) {
        lessonRepository.deleteByLanguageId(languageId);
    }

    @Override
    public void deleteAll(List<Lesson> list) {
        lessonRepository.deleteAll(list);
    }

}
