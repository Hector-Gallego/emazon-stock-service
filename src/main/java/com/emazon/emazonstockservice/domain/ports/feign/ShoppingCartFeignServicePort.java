package com.emazon.emazonstockservice.domain.ports.feign;

import com.emazon.emazonstockservice.domain.model.stock.ArticleCart;
import com.emazon.emazonstockservice.domain.model.stock.CartItem;

import java.util.List;

public interface ShoppingCartFeignServicePort {
    List<CartItem> getArticlesShoppingCart();
}
