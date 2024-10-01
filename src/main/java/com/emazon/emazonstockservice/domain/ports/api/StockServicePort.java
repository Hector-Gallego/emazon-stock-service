package com.emazon.emazonstockservice.domain.ports.api;

import com.emazon.emazonstockservice.domain.model.stock.*;

import java.util.List;

public interface StockServicePort {
    void addStock(Long articleId, Integer quantity);
    StockVerificationResponse checkStockAvailability(StockVerificationRequest stockVerificationRequest);
    PageArticlesCartResponse<ArticleCart> listArticlesCart(PageArticlesCartRequest pageArticlesCartRequest);
    SaleData updateStockAndGetSaleData(List<CartItem> cartItemList);
}
