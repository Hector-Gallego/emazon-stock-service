package com.emazon.emazonstockservice.ports.driven.mysql.mapper.paginationmapper;

import com.emazon.emazonstockservice.domain.model.Article;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.ports.driven.mysql.entity.ArticleEntity;
import com.emazon.emazonstockservice.ports.driven.mysql.mapper.ArticleEntityMapper;
import org.springframework.data.domain.Page;

public class ArticlePageMapper {
    private final ArticleEntityMapper articleEntityMapper;
    public ArticlePageMapper(ArticleEntityMapper articleEntityMapper) {
        this.articleEntityMapper = articleEntityMapper;
    }

    public CustomPage<Article> toCustomPage(Page<ArticleEntity> page) {
        CustomPageMapper<ArticleEntity, Article> mapper = new CustomPageMapper<>(articleEntityMapper::toDomain);
        return mapper.toCustomPage(page);
    }


}
