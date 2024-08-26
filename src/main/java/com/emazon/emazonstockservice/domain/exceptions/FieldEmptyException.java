package com.emazon.emazonstockservice.domain.exceptions;

public class FieldEmptyException extends RuntimeException {

    /**
     * Exception thrown when a required field is found to be empty.
     * This runtime exception is used to indicate that an operation
     * failed because a necessary field was not provided, with a
     * descriptive error message explaining the issue.
     */
    public FieldEmptyException(String message){
        super(message);
    }
}
