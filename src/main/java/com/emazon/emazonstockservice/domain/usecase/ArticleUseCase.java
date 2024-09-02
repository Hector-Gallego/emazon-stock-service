package com.emazon.emazonstockservice.domain.usecase;

import com.emazon.emazonstockservice.domain.api.IArticleServicePort;
import com.emazon.emazonstockservice.domain.exceptions.DuplicateNameException;
import com.emazon.emazonstockservice.domain.model.Article;
import com.emazon.emazonstockservice.domain.spi.IArticlePersistencePort;
import com.emazon.emazonstockservice.domain.util.ArticleValidator;
import com.emazon.emazonstockservice.domain.util.DomainsConstants;


import java.util.List;
import java.util.Set;

public class ArticleUseCase  implements IArticleServicePort {


    private final IArticlePersistencePort articlePersistencePort;


    public ArticleUseCase(IArticlePersistencePort articlePersistencePort) {
        this.articlePersistencePort = articlePersistencePort;
    }


    @Override
    public void saveArticle(Article article, List<Long> categoryIds, Long branId) {

        ArticleValidator.validateCategoryCount(categoryIds);
        ArticleValidator.validateNoDuplicateCategories(categoryIds);

        ArticleValidator.validatePrice(article.getPrice());
        ArticleValidator.validateQuantity(article.getQuantity());
        ArticleValidator.validateName(article.getName());
        ArticleValidator.validateDescription(article.getDescription());

        if (articlePersistencePort.existByName(article.getName())) {
            throw new DuplicateNameException(DomainsConstants.getDuplicateNameFieldMessage(DomainsConstants.MODEL_NAMES.ARTICLE.toString(),article.getName()));
        }


        articlePersistencePort.saveArticle(article, categoryIds, branId);

    }
}
