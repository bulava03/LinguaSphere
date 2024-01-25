package com.example.LinguaSphere.service.impl;

import com.example.LinguaSphere.entity.UserMaterial;
import com.example.LinguaSphere.repository.UserMaterialRepository;
import com.example.LinguaSphere.service.UserMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserMaterialServiceImpl implements UserMaterialService {

    @Autowired
    private UserMaterialRepository userMaterialRepository;

    @Override
    public UserMaterial findById(Long id) {
        return userMaterialRepository.findById(id).orElse(null);
    }

    @Override
    public List<UserMaterial> findAllByUserId(Long userId) {
        return userMaterialRepository.findAllByUserId(userId);
    }

    @Override
    public List<Long> findMaterialsIdsByUserId(Long userId) {
        List<UserMaterial> materialList = findAllByUserId(userId);
        List<Long> list = new ArrayList<>();
        for (UserMaterial material : materialList
             ) {
            list.add(material.getMaterialId());
        }
        return list;
    }

    @Override
    public void save(UserMaterial userMaterial) {
        userMaterialRepository.save(userMaterial);
    }

    @Override
    public void deleteById(Long id) {
        userMaterialRepository.deleteById(id);
    }

}
