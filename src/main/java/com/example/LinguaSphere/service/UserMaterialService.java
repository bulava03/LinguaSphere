package com.example.LinguaSphere.service;

import com.example.LinguaSphere.entity.UserMaterial;

import java.util.List;

public interface UserMaterialService {
    UserMaterial findById(Long id);
    List<UserMaterial> findAllByUserId(Long userId);
    List<Long> findMaterialsIdsByUserId(Long userId);
    void save(UserMaterial userMaterial);
    void deleteById(Long id);
}
