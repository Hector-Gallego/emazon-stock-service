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
    public static final String OPEN_API_INVALID_PARAMETERS = "Invalid parameters supplied";
    public static final String OPENAPI_INTERNAL_SERVER_ERROR = "Internal server error";


    public static final String OPENAPI_CATEGORY_SUMMARY = "Create a new category";
    public static final String OPENAPI_CATEGORY_DESCRIPTION = "Creates a new category with the provided details";

    public static final String CATEGORY_CREATED = "Category created successfully";
    public static final String INVALID_INPUT = "Invalid input";
    public static final String CATEGORY_DATA = "Category Data";

    public static final String BRAND_DATA = "Brand Data";
    public static final String BRAND_CREATED = "Brand created successfully";

    public static final String OPENAPI_BRAND_SUMMARY = "Create a new brand";
    public static final String OPENAPI_BRAND_DESCRIPTION = "Creates a new brand with the provided details";



    public static final String OPENAPI_ARTICLE_SUMMARY = "Create a new category";
    public static final String OPENAPI_ARTICLE_DESCRIPTION = "Creates a new category with the provided details";

    public static final String ARTICLE_DATA = "Article Data";
    public static final String ARTICLE_CREATED = "Article created successfully";





    public static final String OPENAPI_SUMMARY_LIST_CATEGORIES= "List Categories";
    public static final String OPENAPI_DESCRIPTION_LIST_CATEGORIES = "Creates a new category with the provided details";

    public static final String OPEN_API_LIST_CATEGORIES_SUCCESS = "Successfully retrieved categories";



    public static final String OPENAPI_SUMMARY_LIST_BRANDS= "List Brands";
    public static final String OPENAPI_DESCRIPTION_LIST_BRANDS = "Creates a new brand with the provided details";
    public static final String OPEN_API_LIST_BRANDS_SUCCESS = "Successfully retrieved brands";

}
