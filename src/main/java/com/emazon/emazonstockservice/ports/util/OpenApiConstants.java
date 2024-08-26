package com.emazon.emazonstockservice.ports.util;

public final class OpenApiConstants {


    private OpenApiConstants(){
        throw new IllegalStateException();
    }


    public static final String OPENAPI_SUMMARY = "Create a new category";
    public static final String OPENAPI_DESCRIPTION = "Creates a new category with the provided details";
    public static final String OPENAPI_CODE_201 = "201";
    public static final String OPENAPI_CODE_400 = "400";
    public static final String CATEGORY_CREATED = "Category created successfully";
    public static final String INVALID_INPUT = "Invalid input";
    public static final String CATEGORY_DATA = "Category Data";
}
