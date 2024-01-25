package com.example.LinguaSphere.repository;

import com.example.LinguaSphere.entity.UserMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMaterialRepository extends JpaRepository<UserMaterial, Long> {
    List<UserMaterial> findAllByUserId(Long userId);
}
