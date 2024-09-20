package com.emazon.emazonstockservice.domain.spi;

import com.emazon.emazonstockservice.domain.model.Article;
import com.emazon.emazonstockservice.domain.model.Brand;
import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.util.CustomPage;

import java.util.Optional;
import java.util.Set;

public interface ArticlePersistencePort {
    void saveArticle (Article article, Set<Category> categories, Brand brand);
    boolean existByName(String name);
    CustomPage<Article> findAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
    void addStock(Long articleId, Integer quantity);
    Optional<Article> findArticleById(Long articleId);
}
