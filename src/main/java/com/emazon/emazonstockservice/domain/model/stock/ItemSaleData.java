package com.emazon.emazonstockservice.domain.model.stock;

public class ItemSaleData {

    private Long articleId;
    private String articleName;
    private Integer articleQuantity;
    private String articleDescription;

    public ItemSaleData() {
    }

    public ItemSaleData(Long articleId, String articleName, Integer articleQuantity, String articleDescription) {
        this.articleId = articleId;
        this.articleName = articleName;
        this.articleQuantity = articleQuantity;
        this.articleDescription = articleDescription;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public Integer getArticleQuantity() {
        return articleQuantity;
    }

    public void setArticleQuantity(Integer articleQuantity) {
        this.articleQuantity = articleQuantity;
    }

    public String getArticleDescription() {
        return articleDescription;
    }

    public void setArticleDescription(String articleDescription) {
        this.articleDescription = articleDescription;
    }
}
