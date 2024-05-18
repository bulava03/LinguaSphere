package com.example.LinguaSphere.service.impl;

import com.example.LinguaSphere.entity.TeacherParams;
import com.example.LinguaSphere.repository.TeacherParamsRepository;
import com.example.LinguaSphere.service.TeacherParamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherParamsServiceImpl implements TeacherParamsService {

    @Autowired
    private TeacherParamsRepository teacherParamsRepository;

    @Override
    public List<TeacherParams> findAllByIds(List<Long> ids) {
        return teacherParamsRepository.findAllById(ids);
    }

    @Override
    public TeacherParams findByTeacherIdAndLanguageId(Long teacherId, Long languageId) {
        return teacherParamsRepository.findByTeacherIdAndLanguageId(teacherId, languageId);
    }

    @Override
    public void save(TeacherParams teacherParams) {
        teacherParamsRepository.save(teacherParams);
    }

    @Override
    public void saveAll(List<TeacherParams> list) {
        teacherParamsRepository.saveAll(list);
    }

    @Override
    public void deleteById(Long id) {
        teacherParamsRepository.deleteById(id);
    }

    @Override
    public List<TeacherParams> findAllByLanguageId(Long languageId) {
        return teacherParamsRepository.findAllByLanguageId(languageId);
    }

    @Override
    public void deleteAll(List<TeacherParams> list) {
        teacherParamsRepository.deleteAll(list);
    }

}
