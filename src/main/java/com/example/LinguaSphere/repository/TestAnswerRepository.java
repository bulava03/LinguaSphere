package com.example.LinguaSphere.repository;

import com.example.LinguaSphere.entity.TestAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestAnswerRepository extends JpaRepository<TestAnswer, Long> {
}
