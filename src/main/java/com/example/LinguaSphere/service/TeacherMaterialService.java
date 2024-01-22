package com.example.LinguaSphere.service;

import com.example.LinguaSphere.entity.TeacherMaterial;

import java.util.List;

public interface TeacherMaterialService {
    List<Long> findMaterialsByTeacherId(Long teacherId);
    void save(TeacherMaterial teacherMaterial);
}
