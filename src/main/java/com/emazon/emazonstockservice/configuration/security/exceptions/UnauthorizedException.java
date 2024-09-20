package com.emazon.emazonstockservice.configuration.security.exceptions;

import com.emazon.emazonstockservice.domain.constants.ErrorMessagesConstants;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super(ErrorMessagesConstants.UNAUTHORIZED_ERROR_MESSAGE);
    }
}