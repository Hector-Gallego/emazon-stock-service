package com.emazon.emazonstockservice.domain.exceptions;

public class InsufficientStockException extends RuntimeException{

    public InsufficientStockException(String message){
        super(message);
    }
}
