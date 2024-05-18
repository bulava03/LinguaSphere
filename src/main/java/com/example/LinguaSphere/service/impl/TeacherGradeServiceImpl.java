package com.example.LinguaSphere.service.impl;

import com.example.LinguaSphere.entity.TeacherGrade;
import com.example.LinguaSphere.repository.TeacherGradeRepository;
import com.example.LinguaSphere.service.TeacherGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TeacherGradeServiceImpl implements TeacherGradeService {

    @Autowired
    private TeacherGradeRepository teacherGradeRepository;

    @Override
    public TeacherGrade findById(Long id) {
        return teacherGradeRepository.findById(id).orElse(null);
    }

    @Override
    public List<TeacherGrade> findAllByTeacherIdAndLanguageId(Long teacherId, Long languageId) {
        return teacherGradeRepository.findAllByTeacherIdAndLanguageId(teacherId, languageId);
    }

    @Override
    public List<TeacherGrade> findAllByUserIdAndLanguageId(Long userId, Long languageId) {
        return teacherGradeRepository.findAllByUserIdAndLanguageId(userId, languageId);
    }

    @Override
    public TeacherGrade findByTeacherIdAndLanguageIdAndUserId(Long teacherId, Long languageId, Long userId) {
        List<TeacherGrade> list = findAllByTeacherIdAndLanguageId(teacherId, languageId);
        Optional<TeacherGrade> temp = list.stream().filter(element -> Objects.equals(element.getUserId(), userId)).findFirst();
        if (temp.isPresent()) {
            return temp.get();
        } else {
            TeacherGrade tempGrade = new TeacherGrade();
            tempGrade.setLanguageId(languageId);
            tempGrade.setUserId(userId);
            tempGrade.setTeacherId(teacherId);
            tempGrade.setGrade(-1);
            teacherGradeRepository.save(tempGrade);
            return findByTeacherIdAndLanguageIdAndUserId(teacherId, languageId, userId);
        }
    }

    @Override
    public void save(TeacherGrade teacherGrade) {
        teacherGradeRepository.save(teacherGrade);
    }

    @Override
    public void deleteById(Long id) {
        teacherGradeRepository.deleteById(id);
    }

}
