package com.emazon.emazonstockservice.domain.model;

public class ArticleCategory {


    private Long id;
    private Article article;
    private Category category;

    public  ArticleCategory(){

    }
    public ArticleCategory(Long id, Article article, Category category) {
        this.id = id;
        this.article = article;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
