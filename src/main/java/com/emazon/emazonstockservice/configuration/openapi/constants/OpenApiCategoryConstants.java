package com.emazon.emazonstockservice.configuration.openapi.constants;

public class OpenApiCategoryConstants {


    private OpenApiCategoryConstants(){
        throw new IllegalStateException();
    }

    public static final String OPENAPI_CREATE_CATEGORY_SUMMARY = "Create a new category";
    public static final String OPENAPI_CREATE_CATEGORY_DESCRIPTION = "Creates a new category with the provided details";

    public static final String CATEGORY_CREATED = "Category created successfully";

    public static final String OPEN_API_LIST_CATEGORIES_SUCCESS = "Successfully retrieved categories";


}
