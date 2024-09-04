package com.emazon.emazonstockservice.domain.spi;

import com.emazon.emazonstockservice.domain.model.Article;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.ports.driven.entity.BrandEntity;
import com.emazon.emazonstockservice.ports.driven.entity.CategoryEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface IArticlePersistencePort {
    void saveArticle (Article article, Set<CategoryEntity> categoryEntities, BrandEntity brandEntity);
    boolean existByName(String name);
    Optional<BrandEntity> findBrandById(Long brandId);
    CustomPage<Article> findAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
    Map<Long, Optional<CategoryEntity>> findCategoryByIds(List<Long> categoryIds);

}
