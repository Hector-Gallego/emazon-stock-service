package com.emazon.emazonstockservice.configuration.security.exceptions;

import com.emazon.emazonstockservice.domain.constants.ErrorMessagesConstants;

public class GenericFeignException extends RuntimeException{
    public GenericFeignException() {
        super(ErrorMessagesConstants.GENERIC_ERROR_MESSAGE);
    }
}