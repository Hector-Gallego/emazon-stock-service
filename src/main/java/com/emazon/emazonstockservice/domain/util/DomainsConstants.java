package com.emazon.emazonstockservice.domain.util;

public final class DomainsConstants {


    public enum BRAND_FIElDS{
        NAME,
        DESCRIPTION
    }

    public enum CATEGORY_FIELDS{
        NAME,
        DESCRIPTION
    }
    public static final String MAX_NAME_LENGTH = "Name cannot exceed 50 characters.";
    public static final String MAX_DESCRIPTION_LENGTH = "Description cannot exceed 90 characters.";
    public static final String NAME_CANNOT_BE_NULL = "Name cannot be null.";
    public static final String DESCRIPTION_CANNOT_BE_NULL = "Category cannot be null.";
    public static final String NAME_CANNOT_BE_EMPTY = "Name cannot be empty.";
    public static final String DESCRIPTION_CANNOT_BE_EMPTY = "Description cannot be empty.";



    private DomainsConstants(){
        throw new IllegalStateException("Utility class");
    }

    public static String getDuplicateNameFieldMessage( String field, String name) {
        return String.format("A %s with the name '%s' already exists.", field, name);
    }
    public static final String FAIL_SAVE_CATEGORY_MESSAGE = "Filed to save category";
    public static final String FAIL_SAVE_BRAND_MESSAGE = "Filed to save brand";

}
