package com.emazon.emazonstockservice.domain.exceptions;


/**
 * Exception thrown when a duplicate name is detected.
 * This runtime exception indicates that an operation failed
 * due to the presence of a name that already exists, with a
 * descriptive error message provided.
 */
public class DuplicateNameException extends RuntimeException {

    public DuplicateNameException(String message) {
        super(message);
    }
}
