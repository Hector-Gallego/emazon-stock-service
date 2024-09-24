package com.emazon.emazonstockservice.domain.usecase;

import com.emazon.emazonstockservice.domain.constants.ErrorMessagesConstants;
import com.emazon.emazonstockservice.domain.exceptions.DataNotFoundException;
import com.emazon.emazonstockservice.domain.model.Article;
import com.emazon.emazonstockservice.domain.model.StockVerificationRequest;
import com.emazon.emazonstockservice.domain.model.StockVerificationResponse;
import com.emazon.emazonstockservice.domain.ports.api.StockServicePort;
import com.emazon.emazonstockservice.domain.ports.spi.ArticlePersistencePort;
import com.emazon.emazonstockservice.domain.ports.spi.StockPersistencePort;

import java.util.Optional;

public class StockUseCase implements StockServicePort {

    private final StockPersistencePort stockPersistencePort;
    private final ArticlePersistencePort articlePersistencePort;

    public StockUseCase(StockPersistencePort stockPersistencePort, ArticlePersistencePort articlePersistencePort) {
        this.stockPersistencePort = stockPersistencePort;
        this.articlePersistencePort = articlePersistencePort;
    }

    @Override
    public void addStock(Long articleId, Integer quantity) {
        Optional<Article> articleOptional = articlePersistencePort.findArticleById(articleId);

        if(articleOptional.isEmpty()){
            throw new DataNotFoundException(String.format(
                    ErrorMessagesConstants.ARTICLE_NOT_FOUND,
                    articleId));
        }
        stockPersistencePort.addStock(articleId, quantity);
    }

    @Override
    public StockVerificationResponse checkStockAvailability(StockVerificationRequest stockVerificationRequest) {
        return stockPersistencePort.checkStockAvailability(stockVerificationRequest);

    }

}
