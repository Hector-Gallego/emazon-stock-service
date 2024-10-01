package com.emazon.emazonstockservice.ports.driven.feign.adapters;

import com.emazon.emazonstockservice.domain.model.stock.ArticleCart;
import com.emazon.emazonstockservice.domain.model.stock.CartItem;
import com.emazon.emazonstockservice.domain.ports.feign.ShoppingCartFeignServicePort;
import com.emazon.emazonstockservice.ports.driven.feign.clientsinterfaces.ShoppingCartFeignClient;

import java.util.List;

public class ShoppingCartFeignServiceClientAdapter implements ShoppingCartFeignServicePort {

    private final ShoppingCartFeignClient shoppingCartFeignClient;

    public ShoppingCartFeignServiceClientAdapter(ShoppingCartFeignClient shoppingCartFeignClient) {
        this.shoppingCartFeignClient = shoppingCartFeignClient;
    }

    @Override
    public List<CartItem> getArticlesShoppingCart() {
        return shoppingCartFeignClient.getArticlesCart();
    }
}
