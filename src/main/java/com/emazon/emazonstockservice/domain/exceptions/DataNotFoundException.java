package com.emazon.emazonstockservice.domain.exceptions;

public class DataNotFoundException extends RuntimeException{


    public DataNotFoundException(String message){
        super(message);
    }
}
