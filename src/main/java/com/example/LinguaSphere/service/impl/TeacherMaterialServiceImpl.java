package com.example.LinguaSphere.service.impl;

import com.example.LinguaSphere.entity.TeacherMaterial;
import com.example.LinguaSphere.repository.TeacherMaterialRepository;
import com.example.LinguaSphere.service.TeacherMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherMaterialServiceImpl implements TeacherMaterialService {

    @Autowired
    private TeacherMaterialRepository teacherMaterialRepository;

    @Override
    public List<Long> findMaterialsByTeacherId(Long teacherId) {
        List<TeacherMaterial> list = teacherMaterialRepository.findAllByTeacherId(teacherId);
        List<Long> ids = new ArrayList<>();
        for (TeacherMaterial element : list
             ) {
            ids.add(element.getMaterialId());
        }
        return ids;
    }

    @Override
    public void save(TeacherMaterial teacherMaterial) {
        teacherMaterialRepository.save(teacherMaterial);
    }

}
