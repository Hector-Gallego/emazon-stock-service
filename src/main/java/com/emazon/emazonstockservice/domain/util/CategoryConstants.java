package com.emazon.emazonstockservice.domain.util;

public final class CategoryConstants {


    private CategoryConstants(){
        throw new IllegalStateException();
    }

    public static final int MAX_CATEGORY_NAME_LENGTH = 50;
    public static final int MAX_CATEGORY_DESCRIPTION_LENGTH = 90;
    public static final String CATEGORY_NOT_FOUND = "The category with ID %d was not found in the system.";


}
