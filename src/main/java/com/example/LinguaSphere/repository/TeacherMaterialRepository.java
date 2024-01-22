package com.example.LinguaSphere.repository;

import com.example.LinguaSphere.entity.TeacherMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherMaterialRepository extends JpaRepository<TeacherMaterial, Long> {
    List<TeacherMaterial> findAllByTeacherId(Long teacherId);
}
