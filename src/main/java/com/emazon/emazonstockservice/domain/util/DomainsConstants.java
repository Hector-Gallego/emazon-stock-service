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
    public static final String MAX_NAME_LENGTH_MESSAGE = "The field Name cannot exceed %d characters.;";
    public static final String MAX_DESCRIPTION_LENGTH_MESSAGE = "Description cannot exceed %d characters.";

    public static final String NAME_CANNOT_BE_NULL = "Name cannot be null.";
    public static final String DESCRIPTION_CANNOT_BE_NULL = "Category cannot be null.";
    public static final String NAME_CANNOT_BE_EMPTY = "Name cannot be empty.";
    public static final String DESCRIPTION_CANNOT_BE_EMPTY = "Description cannot be empty.";

    public static final int MAX_CATEGORY_NAME_LENGTH = 50;
    public static final int MAX_BRAND_NAME_LENGTH = 50;

    public static final int MAX_CATEGORY_DESCRIPTION_LENGTH = 90;
    public static final int MAX_BRAND_DESCRIPTION_LENGTH = 120;



    private DomainsConstants(){
        throw new IllegalStateException("Utility class");
    }

    public static String getDuplicateNameFieldMessage( String field, String name) {
        return String.format("A %s with the name '%s' already exists.", field, name);
    }


}
