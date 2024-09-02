package com.emazon.emazonstockservice.domain.exceptions;

public class DuplicateCategoryException extends RuntimeException{

    public DuplicateCategoryException(String message){
        super(message);
    }
}
