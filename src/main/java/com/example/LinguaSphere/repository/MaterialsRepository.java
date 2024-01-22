package com.example.LinguaSphere.repository;

import com.example.LinguaSphere.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialsRepository extends JpaRepository<Material, Long> {
}
