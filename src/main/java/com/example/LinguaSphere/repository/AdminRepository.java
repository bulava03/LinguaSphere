package com.example.LinguaSphere.repository;

import com.example.LinguaSphere.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByLogin(String login);
}
