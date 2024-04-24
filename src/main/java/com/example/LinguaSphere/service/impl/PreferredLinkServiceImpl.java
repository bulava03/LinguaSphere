package com.example.LinguaSphere.service.impl;

import com.example.LinguaSphere.entity.PreferredLink;
import com.example.LinguaSphere.repository.PreferredLinkRepository;
import com.example.LinguaSphere.service.PreferredLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PreferredLinkServiceImpl implements PreferredLinkService {

    @Autowired
    private PreferredLinkRepository preferredLinkRepository;

    public Optional<String> findByUserIdAndTeacherId(Long userId, Long teacherId) {
        List<PreferredLink> byTeacher = preferredLinkRepository.findAllByTeacherId(teacherId);
        PreferredLink preferredLink = byTeacher.stream()
                .filter(link -> link.getUserId().equals(userId))
                .findFirst().orElse(null);
        if (preferredLink != null && preferredLink.getPreferredLink() != null) {
            return preferredLink.getPreferredLink().describeConstable();
        } else {
            return Optional.empty();
        }
    }

    public PreferredLink findElementByUserIdAndTeacherId(Long userId, Long teacherId) {
        List<PreferredLink> byTeacher = preferredLinkRepository.findAllByTeacherId(teacherId);
        return byTeacher.stream()
                .filter(link -> link.getUserId().equals(userId))
                .findFirst().orElse(null);
    }

    public List<PreferredLink> findAllByUserId(Long userId) {
        return preferredLinkRepository.findAllByUserId(userId);
    }

    public PreferredLink findById(Long id) {
        return preferredLinkRepository.findById(id).orElse(null);
    }

    public void save(PreferredLink link) {
        preferredLinkRepository.save(link);
    }

}
