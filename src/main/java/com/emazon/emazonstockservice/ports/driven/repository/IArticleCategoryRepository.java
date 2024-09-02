package com.emazon.emazonstockservice.ports.driven.repository;

import com.emazon.emazonstockservice.ports.driven.entity.ArticleCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IArticleCategoryRepository extends JpaRepository<ArticleCategoryEntity, Long> {

}
