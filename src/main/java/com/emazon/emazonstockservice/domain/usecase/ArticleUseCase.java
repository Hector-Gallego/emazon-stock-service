package com.emazon.emazonstockservice.domain.usecase;

import com.emazon.emazonstockservice.domain.api.IArticleServicePort;
import com.emazon.emazonstockservice.domain.exceptions.DataNotFoundException;
import com.emazon.emazonstockservice.domain.exceptions.DuplicateNameException;
import com.emazon.emazonstockservice.domain.model.Article;
import com.emazon.emazonstockservice.domain.spi.IArticlePersistencePort;
import com.emazon.emazonstockservice.domain.util.*;
import com.emazon.emazonstockservice.ports.driven.entity.BrandEntity;
import com.emazon.emazonstockservice.ports.driven.entity.CategoryEntity;

import java.util.*;

public class ArticleUseCase implements IArticleServicePort {


    private final IArticlePersistencePort articlePersistencePort;


    public ArticleUseCase(IArticlePersistencePort articlePersistencePort) {
        this.articlePersistencePort = articlePersistencePort;
    }


    @Override
    public void saveArticle(Article article, List<Long> categoryIds, Long branId) {


        ArticleValidator.validateArticleFields(article, categoryIds, branId);

        if (articlePersistencePort.existByName(article.getName())) {
            throw new DuplicateNameException(DomainsConstants.getDuplicateNameFieldMessage(DomainsConstants.MODEL_NAMES.ARTICLE.toString(), article.getName()));
        }

        BrandEntity brandEntity = articlePersistencePort.findBrandById(branId).orElseThrow(()->
                new DataNotFoundException(String.format(BrandConstants.BRAND_NOT_FOUND, branId)));

        Map<Long, Optional<CategoryEntity>> categoryMap = articlePersistencePort.findCategoryByIds(categoryIds);
        Set<CategoryEntity> categoryEntities = new HashSet<>();

        for (Long categoryId : categoryIds) {
            CategoryEntity categoryEntity = categoryMap.get(categoryId)
                    .orElseThrow(() -> new DataNotFoundException(String.format(
                            CategoryConstants.CATEGORY_NOT_FOUND, categoryId)));
            categoryEntities.add(categoryEntity);
        }



        articlePersistencePort.saveArticle(article, categoryEntities, brandEntity);

    }

    @Override
    public CustomPage<Article> listArticles(Integer pageNo, Integer pageSize, String sortBy, String sortDirection) {

        PaginationValidator.validatePaginationParameters(pageNo, pageSize, sortDirection, sortBy);
        PaginationValidator.validateArticleSortOptionParameters(sortBy);

        return articlePersistencePort.findAll(pageNo, pageSize, sortBy, sortDirection);

    }
}
