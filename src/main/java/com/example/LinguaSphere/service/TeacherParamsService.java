package com.example.LinguaSphere.service;

import com.example.LinguaSphere.entity.TeacherParams;

import java.util.List;

public interface TeacherParamsService {

    List<TeacherParams> findAllByIds(List<Long> ids);
    TeacherParams findByTeacherIdAndLanguageId(Long teacherId, Long languageId);
    void save(TeacherParams teacherParams);
    void saveAll(List<TeacherParams> list);
    void deleteById(Long id);
    List<TeacherParams> findAllByLanguageId(Long languageId);
    void deleteAll(List<TeacherParams> list);

}
