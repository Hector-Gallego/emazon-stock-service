package com.emazon.emazonstockservice.domain.usecase;

import com.emazon.emazonstockservice.domain.api.ArticleServicePort;
import com.emazon.emazonstockservice.domain.constants.BrandConstants;
import com.emazon.emazonstockservice.domain.constants.CategoryConstants;
import com.emazon.emazonstockservice.domain.constants.ErrorMessagesConstants;
import com.emazon.emazonstockservice.domain.constants.ModelNamesConstants;
import com.emazon.emazonstockservice.domain.exceptions.DataNotFoundException;
import com.emazon.emazonstockservice.domain.exceptions.DuplicateNameException;
import com.emazon.emazonstockservice.domain.model.Article;
import com.emazon.emazonstockservice.domain.model.Brand;
import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.spi.ArticlePersistencePort;
import com.emazon.emazonstockservice.domain.spi.BrandPersistencePort;
import com.emazon.emazonstockservice.domain.spi.CategoryPersistencePort;
import com.emazon.emazonstockservice.domain.util.*;
import com.emazon.emazonstockservice.domain.validator.ArticleValidator;
import com.emazon.emazonstockservice.domain.validator.PaginationValidator;

import java.util.*;

public class ArticleUseCase implements ArticleServicePort {


    private final ArticlePersistencePort articlePersistencePort;
    private final CategoryPersistencePort categoryPersistencePort;
    private final BrandPersistencePort brandPersistencePort;


    public ArticleUseCase(ArticlePersistencePort articlePersistencePort, CategoryPersistencePort categoryPersistencePort, BrandPersistencePort brandPersistencePort) {
        this.articlePersistencePort = articlePersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
        this.brandPersistencePort = brandPersistencePort;
    }


    @Override
    public void saveArticle(Article article, List<Long> categoryIds, Long branId) {


        ArticleValidator.validateArticleFields(article, categoryIds, branId);

        if (articlePersistencePort.existByName(article.getName())) {
            throw new DuplicateNameException(ErrorMessagesConstants.getDuplicateNameFieldMessage(ModelNamesConstants.ARTICLE.toString(), article.getName()));
        }

        Brand brand = brandPersistencePort.findBrandById(branId).orElseThrow(()->
                new DataNotFoundException(String.format(BrandConstants.BRAND_NOT_FOUND, branId)));

        Map<Long, Optional<Category>> categoryMap = categoryPersistencePort.findCategoryByIds(categoryIds);
        Set<Category> categories = new HashSet<>();

        for (Long categoryId : categoryIds) {
            Category category = categoryMap.get(categoryId)
                    .orElseThrow(() -> new DataNotFoundException(String.format(
                            CategoryConstants.CATEGORY_NOT_FOUND, categoryId)));
            categories.add(category);
        }

        articlePersistencePort.saveArticle(article, categories, brand);

    }

    @Override
    public CustomPage<Article> listArticles(Integer pageNo, Integer pageSize, String sortBy, String sortDirection) {

        PaginationValidator.validatePaginationParameters(pageNo, pageSize, sortDirection, sortBy);
        PaginationValidator.validateArticleSortOptionParameters(sortBy);

        return articlePersistencePort.findAll(pageNo, pageSize, sortBy, sortDirection);

    }

    @Override
    public void addStock(Long articleId, Integer quantity) {

        articlePersistencePort.findArticleById(articleId).orElseThrow(
                () -> new DataNotFoundException(String.format(
                        ErrorMessagesConstants.ARTICLE_NOT_FOUND,
                        articleId))
        );
        articlePersistencePort.AddStock(articleId, quantity);
    }

}
