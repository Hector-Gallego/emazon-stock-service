package com.emazon.emazonstockservice.ports.driven.mysql.repository;

import com.emazon.emazonstockservice.ports.driven.mysql.entity.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface IArticleRepository extends JpaRepository<ArticleEntity, Long> {
    Optional<ArticleEntity> findByName(String name);

    @Query("SELECT a.quantity FROM ArticleEntity a WHERE a.id = :articleId")
    Optional<Integer> findAvailableStockByArticleId(@Param("articleId") Long articleId);

    List<ArticleEntity> findByIdIn(List<Long> ids);

    @Query("SELECT DISTINCT a FROM ArticleEntity a " +
            "JOIN a.categoryEntities c " +
            "WHERE a.id IN :ids " +
            "AND (:brandName IS NULL OR a.brandEntity.name LIKE %:brandName%) " +
            "AND (:categoryName IS NULL OR c.name LIKE %:categoryName%)")
    Page<ArticleEntity> findByIdInAndFilter(
            @Param("ids") List<Long> ids,
            @Param("brandName") String brandName,
            @Param("categoryName") String categoryName,
            Pageable pageable
    );

}
