package com.example.LinguaSphere.service;

import com.example.LinguaSphere.entity.Material;

import java.util.List;

public interface MaterialsService {
    Material findById(Long id);
    List<Material> findAllById(List<Long> ids);
    void save(Material material);
    void deleteById(Long id);
}
