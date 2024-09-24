package com.emazon.emazonstockservice.ports.driven.mysql.adapter;

import com.emazon.emazonstockservice.domain.constants.ErrorMessagesConstants;
import com.emazon.emazonstockservice.domain.exceptions.DataNotFoundException;
import com.emazon.emazonstockservice.domain.model.StockVerificationRequest;
import com.emazon.emazonstockservice.domain.model.StockVerificationResponse;
import com.emazon.emazonstockservice.domain.ports.spi.StockPersistencePort;
import com.emazon.emazonstockservice.ports.driven.mysql.constants.ValueConstants;
import com.emazon.emazonstockservice.ports.driven.mysql.entity.ArticleEntity;
import com.emazon.emazonstockservice.ports.driven.mysql.entity.CategoryEntity;
import com.emazon.emazonstockservice.ports.driven.mysql.repository.IArticleRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StockJpaAdapter implements StockPersistencePort {

    private final IArticleRepository articleRepository;

    public StockJpaAdapter(IArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }


    @Override
    public void addStock(Long articleId, Integer quantity) {

        Optional<ArticleEntity> articleOpt = articleRepository.findById(articleId);

        if (articleOpt.isPresent()) {
            ArticleEntity article = articleOpt.get();
            article.setQuantity(article.getQuantity() + quantity);
            articleRepository.save(article);
        } else {
            throw new DataNotFoundException(String.format(
                    ErrorMessagesConstants.ARTICLE_NOT_FOUND, articleId));
        }

    }

    @Override
    public StockVerificationResponse checkStockAvailability(StockVerificationRequest request) {
        StockVerificationResponse response = new StockVerificationResponse();
        verifyStockAvailability(request, response);
        verifyCategoryLimit(request, response);
        return response;
    }


    private void verifyStockAvailability(StockVerificationRequest request, StockVerificationResponse response) {

        Integer availableStock = articleRepository.findAvailableStockByArticleId(request.getArticleId())
                .orElseThrow(() -> new DataNotFoundException(String.format(ErrorMessagesConstants.ARTICLE_NOT_FOUND, request.getArticleId())));

        if (availableStock < request.getQuantity()) {
            response.setSufficientStockAvailable(false);
            response.setNextRestockDate(LocalDate.now().plusDays(ValueConstants.MIN_DAYS_FOR_RESTOCKING_SUPPLY));
        }else {
            response.setSufficientStockAvailable(true);
        }
    }

    private void verifyCategoryLimit(StockVerificationRequest request, StockVerificationResponse response) {

        ArticleEntity articleToAdd = articleRepository.findById(request.getArticleId())
                .orElseThrow(() -> new DataNotFoundException(String.format(ErrorMessagesConstants.ARTICLE_NOT_FOUND, request.getArticleId())));

        Map<Long, Integer> categoryCounts = new HashMap<>();

        for (Long cartArticleId : request.getCartArticlesIds()) {

            ArticleEntity existingArticle = articleRepository.findById(cartArticleId)
                    .orElseThrow(() -> new DataNotFoundException(String.format(ErrorMessagesConstants.ARTICLE_NOT_FOUND, cartArticleId)));

            for (CategoryEntity category : existingArticle.getCategoryEntities()) {
                Long categoryId = category.getId();
                categoryCounts.put(
                        categoryId,
                        categoryCounts.getOrDefault(categoryId, ValueConstants.INITIAL_COUNT) + ValueConstants.INCREMENT_ONE);
            }
        }

        for (CategoryEntity category : articleToAdd.getCategoryEntities()) {

            Long categoryId = category.getId();
            int count = categoryCounts.getOrDefault(categoryId, ValueConstants.INITIAL_COUNT);
            if(count >= ValueConstants.MAX_CATEGORY_LIMIT_PER_ARTICLE){
                response.setCategoryLimitExceeded(true);
            }else{
                response.setCategoryLimitExceeded(false);
            }
        }
    }

}
