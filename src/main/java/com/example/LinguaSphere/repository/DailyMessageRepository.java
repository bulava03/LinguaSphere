package com.example.LinguaSphere.repository;

import com.example.LinguaSphere.entity.DailyMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyMessageRepository extends JpaRepository<DailyMessage, Long> {
}
