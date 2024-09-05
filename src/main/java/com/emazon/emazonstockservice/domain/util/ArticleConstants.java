package com.emazon.emazonstockservice.domain.util;

public final class ArticleConstants {

    public static final String VALUE_BRAND_ID_CANNOT_BE_NEGATIVE = "The brand ID must be a non-negative value.";

    private ArticleConstants() {
        throw new IllegalStateException();
    }

    public enum ARTICLE_FIELDS {
        NAME,
        DESCRIPTION,
        QUANTITY,
        PRICE
    }
    public static final String  ARTICLES_RETRIEVED_SUCCESSFULLY ="Articles retrieved successfully";

    public static final String ARTICLE_CREATED_SUCCESSFULLY = "Article created successfully";

    public static final String VALUE_CANNOT_BE_NEGATIVE = "The %s price cannot be less than 0.";
    public static final String CATEGORY_COUNT_OUT_OF_RANGE = "The number of categories associated with the article is not within the allowed range (min: 1, max: 3).";
    public static final String ARTICLE_CANNOT_HAVE_DUPLICATE_CATEGORIES = "The article cannot have duplicate categories.";
}
