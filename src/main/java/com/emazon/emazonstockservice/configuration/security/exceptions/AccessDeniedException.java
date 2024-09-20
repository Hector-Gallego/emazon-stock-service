package com.emazon.emazonstockservice.configuration.security.exceptions;

import com.emazon.emazonstockservice.domain.constants.ErrorMessagesConstants;

public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException(){
        super(ErrorMessagesConstants.ACCESS_DENIED_ERROR_MESSAGE);
    }
}
