package com.emazon.emazonstockservice.domain.ports.spi;

import java.util.Optional;

public interface StockPersistencePort {
    void addStock(Long articleId, Integer quantity);
    Optional<Integer> findAvailableStockByArticleId(Long articleId);

}
