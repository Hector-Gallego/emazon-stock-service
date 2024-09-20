package com.emazon.emazonstockservice.configuration.openapi.constants;

public class OpenApiArticleConstants {


    private OpenApiArticleConstants(){
        throw new IllegalStateException();
    }
    public static final String ARTICLE_CREATED = "Article created successfully";
    public static final String OPENAPI_SUMMARY_LIST_CATEGORIES= "List Categories";
    public static final String OPENAPI_DESCRIPTION_LIST_CATEGORIES = "Retrieves a list of categories based on the provided parameters.";
    public static final String OPENAPI_CREATE_ARTICLE_SUMMARY = "Create a new article";
    public static final String OPENAPI_CREATE_ARTICLE_DESCRIPTION = "Creates a new article with the provided details";

    public static final String OPENAPI_SUMMARY_LIST_ARTICLES= "List Articles";
    public static final String OPENAPI_DESCRIPTION_LIST_ARTICLES = "Retrieves a list of categories based on the provided parameters.";
    public static final String OPEN_API_LIST_ARTICLES_SUCCESS = "Successfully retrieved articles";



    public static final  String SORT_BY_BRAND_NAME = "brand_name";
    public static final  String SORT_BY_CATEGORY_NAME = "category_name";


    public static final String OPENAPI_UPDATE_STOCK_SUMMARY = "Update stock.";
    public static final String OPENAPI_UPDATE_STOCK_DESCRIPTION = "Update stock information for a specific item.";
    public static final String STOCK_UPDATED = "Stock updated successfully.";

}
