package com.emazon.emazonstockservice.domain.spi;

import com.emazon.emazonstockservice.domain.model.Article;

import java.util.List;

public interface IArticlePersistencePort {
    void saveArticle (Article article, List<Long> categoryIds, Long brandId);
    boolean existByName(String name);
}
