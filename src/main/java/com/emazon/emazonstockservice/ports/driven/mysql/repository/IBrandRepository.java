package com.emazon.emazonstockservice.ports.driven.mysql.repository;

import com.emazon.emazonstockservice.ports.driven.mysql.entity.BrandEntity;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IBrandRepository extends JpaRepository<BrandEntity, Long> {
    Optional<BrandEntity> findByName(String name);

    @Nonnull
    Optional<BrandEntity> findById(@Nonnull Long id);
}
