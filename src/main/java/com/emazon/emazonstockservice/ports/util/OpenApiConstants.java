package com.emazon.emazonstockservice.ports.util;

public final class OpenApiConstants {


    private OpenApiConstants(){
        throw new IllegalStateException();
    }

    public static final String OPENAPI_CODE_201 = "201";
    public static final String OPENAPI_CODE_400 = "400";
    public static final String OPENAPI_CODE_500 = "500";
    public static final String OPENAPI_CODE_200 = "200";
    public static final String OPENAPI_MEDIA_TYPE_JSON = "application/json";

    public static final String OPENAPI_INTERNAL_SERVER_ERROR = "Internal server error";

    public static final  String SORT_BY_NAME = "name";
    public static final  String SORT_BY_BRAND_NAME = "brand_name";
    public static final  String SORT_BY_CATEGORY_NAME = "category_name";



    public static final String OPENAPI_CREATE_CATEGORY_SUMMARY = "Create a new category";
    public static final String OPENAPI_CREATE_CATEGORY_DESCRIPTION = "Creates a new category with the provided details";

    public static final String CATEGORY_CREATED = "Category created successfully";
    public static final String INVALID_INPUT = "Invalid input";
    public static final String BRAND_CREATED = "Brand created successfully";

    public static final String OPENAPI_CREATE_BRAND_SUMMARY = "Create a new brand";
    public static final String OPENAPI_CREATE_BRAND_DESCRIPTION = "Creates a new brand with the provided details";



    public static final String OPENAPI_CREATE_ARTICLE_SUMMARY = "Create a new article";
    public static final String OPENAPI_CREATE_ARTICLE_DESCRIPTION = "Creates a new article with the provided details";

    public static final String ARTICLE_CREATED = "Article created successfully";


    public static final String OPENAPI_SUMMARY_LIST_CATEGORIES= "List Categories";
    public static final String OPENAPI_DESCRIPTION_LIST_CATEGORIES = "Retrieves a list of categories based on the provided parameters.";

    public static final String OPEN_API_LIST_CATEGORIES_SUCCESS = "Successfully retrieved categories";

    public static final String OPEN_API_ASC_ORDER = "asc";
    public static final String OPEN_API_DESC_ORDER = "desc";





    public static final String OPENAPI_SUMMARY_LIST_BRANDS= "List Brands";
    public static final String OPENAPI_DESCRIPTION_LIST_BRANDS = "Retrieves a list of brands based on the provided parameters.";
    public static final String OPEN_API_LIST_BRANDS_SUCCESS = "Successfully retrieved brands";


    public static final String OPENAPI_SUMMARY_LIST_ARTICLES= "List Articles";
    public static final String OPENAPI_DESCRIPTION_LIST_ARTICLES = "Retrieves a list of categories based on the provided parameters.";
    public static final String OPEN_API_LIST_ARTICLES_SUCCESS = "Successfully retrieved articles";

}
