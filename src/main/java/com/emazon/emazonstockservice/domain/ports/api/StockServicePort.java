package com.emazon.emazonstockservice.domain.ports.api;

import com.emazon.emazonstockservice.domain.model.StockVerificationRequest;
import com.emazon.emazonstockservice.domain.model.StockVerificationResponse;

public interface StockServicePort {
    void addStock(Long articleId, Integer quantity);
    StockVerificationResponse checkStockAvailability(StockVerificationRequest stockVerificationRequest);
}
