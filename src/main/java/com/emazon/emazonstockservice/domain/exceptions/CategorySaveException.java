package com.emazon.emazonstockservice.domain.exceptions;


/**
 * Exception thrown when there is an error while saving a category.
 * This runtime exception indicates that the category could not be saved
 * due to some issue, providing a descriptive error message.
 */
public class CategorySaveException extends RuntimeException{

    public CategorySaveException(String message){
        super(message);
    }
}
