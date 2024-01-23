package com.example.LinguaSphere.service.impl;

import com.example.LinguaSphere.entity.Material;
import com.example.LinguaSphere.repository.MaterialsRepository;
import com.example.LinguaSphere.service.MaterialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialsService {

    @Autowired
    private MaterialsRepository materialsRepository;

    @Override
    public Material findById(Long id) {
        return materialsRepository.findById(id).orElse(null);
    }

    @Override
    public List<Material> findAllById(List<Long> ids) {
        return materialsRepository.findAllById(ids);
    }

    @Override
    public void save(Material material) {
        materialsRepository.save(material);
    }

    @Override
    public void deleteById(Long id) {
        materialsRepository.deleteById(id);
    }

}
