package com.emazon.emazonstockservice.domain.ports.spi;

import com.emazon.emazonstockservice.domain.model.*;
import com.emazon.emazonstockservice.domain.util.CustomPage;

import java.util.Optional;
import java.util.Set;

public interface ArticlePersistencePort {
    void saveArticle (Article article, Set<Category> categories, Brand brand);
    boolean existByName(String name);
    CustomPage<Article> findAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
    Optional<Article> findArticleById(Long articleId);
}
