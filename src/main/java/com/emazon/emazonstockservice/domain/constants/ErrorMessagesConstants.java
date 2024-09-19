package com.emazon.emazonstockservice.domain.constants;

public final class ErrorMessagesConstants {



    private ErrorMessagesConstants(){
        throw new IllegalStateException();
    }



    public static final String MAX_NAME_LENGTH_MESSAGE = "The field Name cannot exceed %d characters.;";
    public static final String MAX_DESCRIPTION_LENGTH_MESSAGE = "Description cannot exceed %d characters.";

    public static final String NAME_CANNOT_BE_NULL = "Name cannot be null.";
    public static final String DESCRIPTION_CANNOT_BE_NULL = "Description cannot be null.";
    public static final String NAME_CANNOT_BE_EMPTY = "Name cannot be empty.";
    public static final String DESCRIPTION_CANNOT_BE_EMPTY = "Description cannot be empty.";

    public static final String INVALID_PAGE_NO = "Invalid page number. It must be a non-negative integer.";
    public static final String INVALID_PAGE_SIZE = "Invalid page size. It must be a positive integer.";
    public static final String INVALID_SORT_DIRECTION = "Invalid sort direction. It must be either 'asc' or 'desc'.";
    public static final String INVALID_SORT_BY = "Invalid sort field";
    public static final String INVALID_PARAMETERS_MESSAGE = "One or more parameters are invalid.";

    public static final String ARTICLE_NOT_FOUND = "Article with id: %d not found";

    public static String getDuplicateNameFieldMessage( String field, String name) {
        return String.format("A %s with the name '%s' already exists.", field, name);
    }


}
