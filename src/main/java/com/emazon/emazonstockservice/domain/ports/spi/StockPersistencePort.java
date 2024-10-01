package com.emazon.emazonstockservice.domain.ports.spi;

import com.emazon.emazonstockservice.domain.model.Article;
import com.emazon.emazonstockservice.domain.model.stock.ArticleCart;
import com.emazon.emazonstockservice.domain.model.stock.CartItem;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.domain.model.stock.PageArticlesCartRequest;

import java.util.List;
import java.util.Optional;

public interface StockPersistencePort {
    void addStock(Long articleId, Integer quantity);
    Optional<Integer> findAvailableStockByArticleId(Long articleId);
    CustomPage<ArticleCart> getPageArticlesCart(PageArticlesCartRequest pageArticlesCartRequest, List<CartItem> articleCarts);
    List<Article> getTotalArticlesCart(List<Long> ids);
    void updateStock(List<CartItem> cartItems);


}
