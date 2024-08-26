package com.emazon.emazonstockservice.domain.exceptions;


/**
 * Exception thrown when a field exceeds its allowed limit.
 * This runtime exception is used to indicate that an operation
 * failed because the size or length of a field surpassed the
 * defined limit, with a descriptive error message explaining the issue.
 */
public class FieldLimitExceededException extends RuntimeException{
    public FieldLimitExceededException(String message) {
        super(message);
    }
}
