package com.emazon.emazonstockservice.ports.driven.repository;

import com.emazon.emazonstockservice.ports.driven.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface IArticleRepository extends JpaRepository<ArticleEntity, Long> {
    Optional<ArticleEntity> findByName(String name);

}
