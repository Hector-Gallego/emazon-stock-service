package com.emazon.emazonstockservice.ports.util;

public final class OpenApiConstants {


    private OpenApiConstants(){
        throw new IllegalStateException();
    }


    public static final String OPENAPI_SUMMARY = "Create a new category";
    public static final String OPENAPI_DESCRIPTION = "Creates a new category with the provided details";
    public static final String OPENAPI_CODE_201 = "201";
    public static final String OPENAPI_CODE_400 = "400";
    public static final String OPENAPI_CODE_500 = "500";
    public static final String CATEGORY_CREATED = "Category created successfully";
    public static final String INVALID_INPUT = "Invalid input";
    public static final String CATEGORY_DATA = "Category Data";

    public static final String OPENAPI_SUMMARY_LIST_CATEGORIES= "List Categories";
    public static final String OPENAPI_DESCRIPTION_LIST_CATEGORIES = "Creates a new category with the provided details";
    public static final String OPENAPI_CODE_200 = "200";
    public static final String OPEN_API_LIST_CATEGORIES_SUCCESS = "Successfully retrieved categories";
    public static final String OPENAPI_MEDIA_TYPE_JSON = "application/json";
    public static final String OPEN_API_INVALID_PARAMETERS = "Invalid parameters supplied";
    public static final String OPENAPI_INTERNAL_SERVER_ERROR = "Internal server error";


}
