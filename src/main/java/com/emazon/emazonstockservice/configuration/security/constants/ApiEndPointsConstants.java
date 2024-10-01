package com.emazon.emazonstockservice.configuration.security.constants;

public final class ApiEndPointsConstants {

    private ApiEndPointsConstants(){
        throw new IllegalStateException();
    }
    public static final String API_CATEGORY_URI = "/api/category";
    public static final String API_BRAND_URI = "/api/brand";
    public static final String API_ARTICLE_URI = "/api/article/save";
    public static final String API_STOCK_URI = "/api/stock";
    public static final String API_STOCK_LIST_CART_URI = "/api/stock/listCart";
}
