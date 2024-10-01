package com.emazon.emazonstockservice.domain.validator;

import com.emazon.emazonstockservice.domain.constants.ErrorMessagesConstants;
import com.emazon.emazonstockservice.domain.exceptions.DataNotFoundException;
import com.emazon.emazonstockservice.domain.model.Article;
import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.model.stock.StockVerificationRequest;
import com.emazon.emazonstockservice.domain.model.stock.StockVerificationResponse;
import com.emazon.emazonstockservice.domain.ports.spi.ArticlePersistencePort;
import com.emazon.emazonstockservice.domain.ports.spi.StockPersistencePort;
import com.emazon.emazonstockservice.ports.driven.mysql.constants.ValueConstants;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class StockValidator {


    private final StockPersistencePort stockPersistencePort;
    private final ArticlePersistencePort articlePersistencePort;


    public StockValidator(StockPersistencePort stockPersistencePort,
                          ArticlePersistencePort articlePersistencePort) {

        this.stockPersistencePort = stockPersistencePort;
        this.articlePersistencePort = articlePersistencePort;

    }

    public StockVerificationResponse checkStockAvailability(StockVerificationRequest request) {

        StockVerificationResponse response = new StockVerificationResponse();
        verifyStockAvailability(request, response);
        verifyCategoryLimit(request, response);

        return response;
    }


    private void verifyStockAvailability(StockVerificationRequest request, StockVerificationResponse response) {

        Integer availableStock = stockPersistencePort.findAvailableStockByArticleId(request.getArticleId())
                .orElseThrow(() -> new DataNotFoundException(String.format(ErrorMessagesConstants.ARTICLE_NOT_FOUND, request.getArticleId())));

        if (availableStock < request.getQuantity()) {
            response.setSufficientStockAvailable(false);
            response.setNextRestockDate(LocalDate.now().plusDays(ValueConstants.MIN_DAYS_FOR_RESTOCKING_SUPPLY));
        }else {
            response.setSufficientStockAvailable(true);
        }
    }
    private void verifyCategoryLimit(StockVerificationRequest request, StockVerificationResponse response) {

        Article articleToAdd = findArticleOrThrow(request.getArticleId());

        Map<Long, Integer> categoryCounts = new HashMap<>();


        for (Long cartArticleId : request.getCartArticlesIds()) {
            Article existingArticle = findArticleOrThrow(cartArticleId);
            countCategories(categoryCounts, existingArticle);
        }

        for (Category category : articleToAdd.getCategories()) {
            Long categoryId = category.getId();
            int count = categoryCounts.getOrDefault(categoryId, ValueConstants.INITIAL_COUNT);

            if (count >= ValueConstants.MAX_CATEGORY_LIMIT_PER_ARTICLE) {
                response.setCategoryLimitExceeded(true);
                return;
            }
        }
        response.setCategoryLimitExceeded(false);
    }


    private Article findArticleOrThrow(Long articleId) {
        return articlePersistencePort.findArticleById(articleId)
                .orElseThrow(() -> new DataNotFoundException(String.format(ErrorMessagesConstants.ARTICLE_NOT_FOUND, articleId)));
    }


    private void countCategories(Map<Long, Integer> categoryCounts, Article article) {
        for (Category category : article.getCategories()) {
            Long categoryId = category.getId();
            categoryCounts.put(categoryId, categoryCounts.getOrDefault(categoryId, ValueConstants.INITIAL_COUNT) + ValueConstants.INCREMENT_ONE);
        }
    }


}
