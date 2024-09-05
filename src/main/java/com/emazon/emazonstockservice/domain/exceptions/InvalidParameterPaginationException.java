package com.emazon.emazonstockservice.domain.exceptions;

import java.util.List;

public class InvalidParameterPaginationException extends RuntimeException {

    private final List<String> errors;
    public InvalidParameterPaginationException(String message, List<String> errors){
        super(message);
        this.errors = errors;

    }
    public List<String> getErrors() {
        return errors;
    }
}
