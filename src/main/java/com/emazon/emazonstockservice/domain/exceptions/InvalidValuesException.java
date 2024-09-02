package com.emazon.emazonstockservice.domain.exceptions;

public class InvalidValuesException extends RuntimeException{

    public InvalidValuesException(String message){
        super(message);
    }
}
