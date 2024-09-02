package com.emazon.emazonstockservice.domain.api;

import com.emazon.emazonstockservice.domain.model.Article;
import com.emazon.emazonstockservice.ports.driven.entity.ArticleEntity;

import java.util.List;
import java.util.Set;

public interface IArticleServicePort {
    void saveArticle(Article article, List<Long> categoryIds, Long brandId);

}
