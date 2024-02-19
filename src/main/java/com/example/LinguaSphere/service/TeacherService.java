package com.example.LinguaSphere.service;

import com.example.LinguaSphere.entity.Teacher;

import java.util.List;

public interface TeacherService {
    Object[] validateTeacher(Teacher teacher);
    List<Teacher> findAll();
    Teacher findById(Long id);
    Teacher findByEmail(String email);
    void save(Teacher teacher);
    void deleteById(Long id);
}
