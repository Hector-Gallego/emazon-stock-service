package com.emazon.emazonstockservice.domain.exceptions;

public class BrandSaveException extends RuntimeException{
    public BrandSaveException(String message){
        super(message);
    }
}
