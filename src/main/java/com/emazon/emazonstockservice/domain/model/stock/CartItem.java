package com.emazon.emazonstockservice.domain.model.stock;

public class CartItem {

    private Long articleId;
    private Integer quantity;

    public CartItem() {
    }

    public CartItem(Long articleId, Integer quantity) {
        this.articleId = articleId;
        this.quantity = quantity;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
