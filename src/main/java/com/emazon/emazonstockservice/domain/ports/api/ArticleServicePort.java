package com.emazon.emazonstockservice.domain.ports.api;

import com.emazon.emazonstockservice.domain.model.Article;
import com.emazon.emazonstockservice.domain.util.CustomPage;

import java.util.List;


public interface ArticleServicePort {
    void saveArticle(Article article, List<Long> categoryIds, Long brandId);
    CustomPage<Article> listArticles(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
    Article getArticleById(Long id);
}
