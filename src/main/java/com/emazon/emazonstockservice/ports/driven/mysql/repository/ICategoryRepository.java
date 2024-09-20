package com.emazon.emazonstockservice.ports.driven.mysql.repository;


import com.emazon.emazonstockservice.ports.driven.mysql.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByName(String name);
}
