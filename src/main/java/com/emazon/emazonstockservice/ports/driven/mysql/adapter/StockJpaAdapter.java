package com.emazon.emazonstockservice.ports.driven.mysql.adapter;

import com.emazon.emazonstockservice.domain.constants.ErrorMessagesConstants;
import com.emazon.emazonstockservice.domain.exceptions.DataNotFoundException;
import com.emazon.emazonstockservice.domain.ports.spi.StockPersistencePort;
import com.emazon.emazonstockservice.ports.driven.mysql.entity.ArticleEntity;
import com.emazon.emazonstockservice.ports.driven.mysql.repository.IArticleRepository;

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
    public Optional<Integer> findAvailableStockByArticleId(Long articleId) {
        return articleRepository.findAvailableStockByArticleId(articleId);
    }

}
