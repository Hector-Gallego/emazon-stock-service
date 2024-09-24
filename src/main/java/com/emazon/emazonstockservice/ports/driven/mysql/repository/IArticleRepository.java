package com.emazon.emazonstockservice.ports.driven.mysql.repository;

import com.emazon.emazonstockservice.ports.driven.mysql.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Optional;

public interface IArticleRepository extends JpaRepository<ArticleEntity, Long> {
    Optional<ArticleEntity> findByName(String name);
    @Query("SELECT a.quantity FROM ArticleEntity a WHERE a.id = :articleId")
    Optional<Integer> findAvailableStockByArticleId(@Param("articleId") Long articleId);

}
