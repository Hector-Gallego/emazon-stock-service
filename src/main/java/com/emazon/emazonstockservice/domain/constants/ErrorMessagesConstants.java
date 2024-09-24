package com.emazon.emazonstockservice.domain.constants;

public final class ErrorMessagesConstants {



    private ErrorMessagesConstants(){
        throw new IllegalStateException();
    }



    public static final String MAX_NAME_LENGTH_ERROR_MESSAGE = "The field Name cannot exceed %d characters.;";
    public static final String MAX_DESCRIPTION_LENGTH_ERROR_MESSAGE = "Description cannot exceed %d characters.";

    public static final String NAME_CANNOT_BE_NULL_ERROR_MESSAGE = "Name cannot be null.";
    public static final String DESCRIPTION_CANNOT_BE_NULL_ERROR_MESSAGE = "Description cannot be null.";
    public static final String NAME_CANNOT_BE_EMPTY_ERROR_MESSAGE = "Name cannot be empty.";
    public static final String DESCRIPTION_CANNOT_BE_EMPTY_ERROR_MESSAGE = "Description cannot be empty.";

    public static final String INVALID_PAGE_NO_ERROR_MESSAGE = "Invalid page number. It must be a non-negative integer.";
    public static final String INVALID_PAGE_SIZE_ERROR_MESSAGE = "Invalid page size. It must be a positive integer.";
    public static final String INVALID_SORT_DIRECTION_ERROR_MESSAGE = "Invalid sort direction. It must be either 'asc' or 'desc'.";
    public static final String INVALID_SORT_BY_ERROR_MESSAGE = "Invalid sort field: ";
    public static final String INVALID_PARAMETERS_ERROR_MESSAGE = "One or more parameters are invalid.";

    public static final String ARTICLE_NOT_FOUND = "Article with id: %d not found";

    public static final String BAD_REQUEST_ERROR_MESSAGE = "The request could not be processed due to invalid input.";
    public static final String GENERIC_ERROR_MESSAGE = "An unexpected error occurred. Please try again later.";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE= "An internal server error occurred. Please contact support.";
    public static final String NOT_FOUND_ERROR_MESSAGE = "The requested resource was not found.";
    public static final String UNAUTHORIZED_ERROR_MESSAGE = "You are not authorized to access this resource.";
    public static final String ACCESS_DENIED_ERROR_MESSAGE = "Access to the requested resource is denied.";

    public static final String QUANTITY_CANNOT_BE_NULL_ERROR_MESSAGE = "Quantity cannot be null";
    public static final String QUANTITY_MUST_BE_POSITIVE_OR_ZERO_ERROR_MESSAGE = "Quantity must be zero or a positive number";
    public static final String PRICE_CANNOT_BE_NULL_ERROR_MESSAGE = "Price cannot be null";
    public static final String PRICE_MUST_BE_POSITIVE_OR_ZERO_ERROR_MESSAGE = "Price must be zero or a positive number";
    public static final String INVALID_FIELDS = "One or more fields are invalid";
    public static final String ARTICLE_NOT_FOUND_ERROR_MESSAGE = "Article %d not found.";


    public static String getDuplicateNameFieldMessage( String field, String name) {
        return String.format("A %s with the name '%s' already exists.", field, name);
    }


}
