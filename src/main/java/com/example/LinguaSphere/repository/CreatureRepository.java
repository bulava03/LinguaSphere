package com.example.LinguaSphere.repository;

import com.example.LinguaSphere.entity.Creature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreatureRepository extends JpaRepository<Creature, Long> {
    List<Creature> findAllByLanguageId(Long languageId);
}
