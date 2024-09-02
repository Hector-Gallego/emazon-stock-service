package com.emazon.emazonstockservice.domain.exceptions;

public class InvalidCategoryCountException extends RuntimeException{

    public InvalidCategoryCountException(String message){
        super(message);
    }

}
