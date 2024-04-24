package com.example.LinguaSphere.repository;

import com.example.LinguaSphere.entity.PreferredLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreferredLinkRepository extends JpaRepository<PreferredLink, Long> {
    List<PreferredLink> findAllByTeacherId(Long teacherId);
    List<PreferredLink> findAllByUserId(Long userId);
}
