package com.emazon.emazonstockservice.domain.api;

import com.emazon.emazonstockservice.domain.model.Article;
import com.emazon.emazonstockservice.domain.util.CustomPage;

import java.util.List;


public interface IArticleServicePort {
    void saveArticle(Article article, List<Long> categoryIds, Long brandId);
    CustomPage<Article> listArticles(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);


}
