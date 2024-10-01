package com.emazon.emazonstockservice.ports.driven.mysql.mapper.paginationmapper;

import com.emazon.emazonstockservice.domain.model.stock.ArticleCart;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.ports.driven.mysql.entity.ArticleEntity;
import com.emazon.emazonstockservice.ports.driven.mysql.mapper.ArticleCartEntityMapper;
import org.springframework.data.domain.Page;

public class ArticleCartPageMapper {
    private final ArticleCartEntityMapper articleCartEntityMapper;

    public ArticleCartPageMapper(ArticleCartEntityMapper articleCartEntityMapper) {
        this.articleCartEntityMapper = articleCartEntityMapper;
    }

    public CustomPage<ArticleCart> toCustomPage(Page<ArticleEntity> page) {
        CustomPageMapper<ArticleEntity, ArticleCart> mapper = new CustomPageMapper<>(articleCartEntityMapper::toDomain);
        return mapper.toCustomPage(page);
    }

}
