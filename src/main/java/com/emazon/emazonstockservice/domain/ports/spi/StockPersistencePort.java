package com.emazon.emazonstockservice.domain.ports.spi;

import com.emazon.emazonstockservice.domain.model.StockVerificationRequest;
import com.emazon.emazonstockservice.domain.model.StockVerificationResponse;

public interface StockPersistencePort {
    void addStock(Long articleId, Integer quantity);
    StockVerificationResponse checkStockAvailability(StockVerificationRequest stockVerificationRequest);


}
