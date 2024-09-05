package com.emazon.emazonstockservice.domain.util;

public final class BrandConstants {

    private BrandConstants() {
        throw new IllegalStateException();
    }
    public static final String BRAND_CREATED_SUCCESSFULLY = "Brand created successfully";
    public static final String MAX_BRAND_DESCRIPTION_LENGTH_MESSAGE = "Description cannot exceed 120 characters.";
    public static final String MAX_BRAND_NAME_LENGTH_MESSAGE = "Name cannot exceed 50 characters.";

    public static final int MAX_BRAND_NAME_LENGTH = 50;

    public static final int MAX_BRAND_DESCRIPTION_LENGTH = 120;
    public static final String BRAND_NOT_FOUND = "The Brand with ID %d was not found in the system.";
    public static final String BRAND_ID_CANNOT_BE_NULL = "Brand ID cannot be null";

    public static final String  BRANDS_RETRIEVED_SUCCESSFULLY ="Brands retrieved successfully";

}
