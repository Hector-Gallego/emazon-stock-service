package com.emazon.emazonstockservice.domain.util;

public final class CategoryConstants {


    private CategoryConstants(){
        throw new IllegalStateException();
    }

    public static final int MAX_CATEGORY_NAME_LENGTH = 50;
    public static final int MAX_CATEGORY_DESCRIPTION_LENGTH = 90;
    public static final String CATEGORY_NOT_FOUND = "The category with ID %d was not found in the system.";

    public static final String CATEGORY_CREATED_SUCCESSFULLY = "Category created successfully";
    public static final String  CATEGORIES_RETRIEVED_SUCCESSFULLY ="Categories retrieved successfully";
    public static final String CATEGORY_IDS_CANNOT_BE_NULL = "Category IDs cannot be null";
    public static final String CATEGORY_IDS_CANNOT_BE_EMPTY = "Category IDs cannot be empty";
    public static final String MAX_CATEGORY_DESCRIPTION_LENGTH_MESSAGE = "Description cannot exceed 90 characters.";
    public static final String MAX_CATEGORY_NAME_LENGTH_MESSAGE = "Name cannot exceed 50 characters.";

}
