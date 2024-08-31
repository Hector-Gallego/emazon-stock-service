package com.emazon.emazonstockservice.domain.util;

public final class DomainsConstants {

    public enum MODEL_NAMES{
        CATEGORY,
        BRAND
    }



    public static final String MAX_NAME_LENGTH_MESSAGE = "The field Name cannot exceed %d characters.;";
    public static final String MAX_DESCRIPTION_LENGTH_MESSAGE = "Description cannot exceed %d characters.";

    public static final String NAME_CANNOT_BE_NULL = "Name cannot be null.";
    public static final String DESCRIPTION_CANNOT_BE_NULL = "Description cannot be null.";
    public static final String NAME_CANNOT_BE_EMPTY = "Name cannot be empty.";
    public static final String DESCRIPTION_CANNOT_BE_EMPTY = "Description cannot be empty.";






    private DomainsConstants(){
        throw new IllegalStateException("Utility class");
    }

    public static String getDuplicateNameFieldMessage( String field, String name) {
        return String.format("A %s with the name '%s' already exists.", field, name);
    }


}
