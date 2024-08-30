package com.emazon.emazonstockservice.ports.util;

public class PortsConstants {

    public static final String CATEGORY_CREATED_SUCCESSFULLY = "Category created successfully";
    public static final String  CATEGORIES_RETRIEVED_SUCCESSFULLY ="Categories retrieved successfully";

    public static final String BRAND_CREATED_SUCCESSFULLY = "Brand created successfully";
    public static final String  BRANDS_RETRIEVED_SUCCESSFULLY ="Brands retrieved successfully";

    private PortsConstants(){
        throw new IllegalStateException();
    }


    public static final int MAX_BRAND_NAME_LENGTH = 50;
    public static final int MAX_CATEGORY_NAME_LENGTH = 50;

    public static final int MAX_CATEGORY_DESCRIPTION_LENGTH = 90;
    public static final int MAX_BRAND_DESCRIPTION_LENGTH = 120;



    public static final String MAX_CATEGORY_DESCRIPTION_LENGTH_MESSAGE = "Description cannot exceed 90 characters.";
    public static final String MAX_BRAND_DESCRIPTION_LENGTH_MESSAGE = "Description cannot exceed 120 characters.";

    public static final String DESCRIPTION_CANNOT_BE_NULL = "Description cannot be null.";
    public static final String DESCRIPTION_CANNOT_BE_EMPTY = "Description cannot be empty.";



    public static final String MAX_CATEGORY_NAME_LENGTH_MESSAGE = "Name cannot exceed 50 characters.";
    public static final String MAX_BRAND_NAME_LENGTH_MESSAGE = "Name cannot exceed 50 characters.";
    public static final String NAME_CANNOT_BE_NULL = "Name cannot be null.";
    public static final String NAME_CANNOT_BE_EMPTY = "Name cannot be empty.";

}
