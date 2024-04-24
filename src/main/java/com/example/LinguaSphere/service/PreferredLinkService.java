package com.example.LinguaSphere.service;

import com.example.LinguaSphere.entity.PreferredLink;

import java.util.List;
import java.util.Optional;

public interface PreferredLinkService {
    Optional<String> findByUserIdAndTeacherId(Long userId, Long teacherId);
    PreferredLink findElementByUserIdAndTeacherId(Long userId, Long teacherId);
    List<PreferredLink> findAllByUserId(Long userId);
    PreferredLink findById(Long id);
    void save(PreferredLink link);
}
