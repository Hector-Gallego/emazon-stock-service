package com.emazon.emazonstockservice.ports.driven.feign.clientsinterfaces;

import com.emazon.emazonstockservice.domain.model.stock.ArticleCart;
import com.emazon.emazonstockservice.domain.model.stock.CartItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "emazon-shopping-cart-service", url = "http://localhost:9020")
public interface ShoppingCartFeignClient {

    @GetMapping("api/cart/getArticlesCart")
    List<CartItem> getArticlesCart();


}
