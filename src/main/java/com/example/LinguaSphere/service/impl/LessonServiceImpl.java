package com.example.LinguaSphere.service.impl;

import com.example.LinguaSphere.entity.Lesson;
import com.example.LinguaSphere.entity.Teacher;
import com.example.LinguaSphere.entity.TeacherLanguage;
import com.example.LinguaSphere.entity.User;
import com.example.LinguaSphere.entity.dto.LessonTemplate;
import com.example.LinguaSphere.repository.LessonRepository;
import com.example.LinguaSphere.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public List<Lesson> findAllByTeacherIds(List<Long> list) {
        List<Lesson> resultList = new ArrayList<>();
        for (Long element : list
             ) {
            resultList.addAll(lessonRepository.findAllByTeacherId(element));
        }
        return resultList;
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
    @Transactional
    public void deleteByLanguageId(Long languageId) {
        lessonRepository.deleteByLanguageId(languageId);
    }

    @Override
    public void deleteAll(List<Lesson> list) {
        lessonRepository.deleteAll(list);
    }

    @Override
    public void setLessonForUser(Long userId, Teacher teacher, LessonTemplate lesson) {
        if (teacher != null) {
            List<Lesson> teachersLessons = findAllByTeacherId(teacher.getId());
            Lesson lessonToSave = new Lesson();
            for (Lesson element : teachersLessons
            ) {
                if (element.getDay() == lesson.getDay() && element.getTime() == lesson.getTime()) {
                    lessonToSave = element;
                    break;
                }
            }

            if (userId != null) {
                lessonToSave.setLanguageId(lesson.getLanguageId());
            } else {
                lessonToSave.setLanguageId(null);
            }

            lessonToSave.setUserId(userId);
            save(lessonToSave);
        }
    }

}
