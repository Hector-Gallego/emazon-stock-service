package com.emazon.emazonstockservice.configuration.exception.execptionhandle;

import com.emazon.emazonstockservice.configuration.security.exceptions.AccessDeniedException;
import com.emazon.emazonstockservice.configuration.security.exceptions.GenericFeignException;
import com.emazon.emazonstockservice.configuration.security.exceptions.UnauthorizedException;
import com.emazon.emazonstockservice.domain.constants.ErrorMessagesConstants;
import com.emazon.emazonstockservice.domain.exceptions.DuplicateNameException;
import com.emazon.emazonstockservice.domain.exceptions.FieldEmptyException;
import com.emazon.emazonstockservice.domain.exceptions.FieldLimitExceededException;
import com.emazon.emazonstockservice.domain.exceptions.InvalidParameterPaginationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FieldLimitExceededException.class)
    public ResponseEntity<CustomErrorResponse> handleFieldLimitExceededException(FieldLimitExceededException exception) {
        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST, Collections.emptyList());
    }

    @ExceptionHandler(FieldEmptyException.class)
    public ResponseEntity<CustomErrorResponse> handleFieldEmptyException(FieldEmptyException exception) {
        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST, Collections.emptyList());
    }

    @ExceptionHandler(DuplicateNameException.class)
    public ResponseEntity<CustomErrorResponse> handleDuplicateNameException(DuplicateNameException exception) {
        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST, Collections.emptyList());
    }

    @ExceptionHandler(InvalidParameterPaginationException.class)
    public ResponseEntity<CustomErrorResponse> handleParameterInvalidException(InvalidParameterPaginationException exception) {
        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST, exception.getErrors());
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomErrorResponse> handleAccessDeniedExceptions(AccessDeniedException exception) {
        return buildErrorResponse(exception, HttpStatus.FORBIDDEN, Collections.emptyList());
    }


    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<CustomErrorResponse> handleUnauthorizedException(UnauthorizedException exception) {
        return buildErrorResponse(exception, HttpStatus.UNAUTHORIZED, Collections.emptyList());
    }

    @ExceptionHandler(GenericFeignException.class)
    public ResponseEntity<CustomErrorResponse> handleGenericException(GenericFeignException exception) {
        return buildErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR, Collections.emptyList());
    }



    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            @Nullable HttpHeaders headers,
            @Nullable HttpStatusCode status,
            @Nullable WebRequest request) {

        List<String> errorList = exception
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        CustomErrorResponse response = new CustomErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ErrorMessagesConstants.INVALID_FIELDS,
                errorList,
                LocalDateTime.now()

        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CustomErrorResponse> handleGeneralException(RuntimeException exception) {
        HttpStatus status;

        if (exception instanceof IllegalArgumentException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (exception instanceof NullPointerException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (exception instanceof EntityNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        }  else if (exception instanceof HttpMessageNotReadableException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (exception instanceof DataIntegrityViolationException) {
            status = HttpStatus.CONFLICT;
        } else if (exception instanceof ConcurrencyFailureException) {
            status = HttpStatus.LOCKED;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return buildErrorResponse(exception, status, Collections.emptyList());
    }

    private ResponseEntity<CustomErrorResponse> buildErrorResponse(Exception exception, HttpStatus status, List<String> errors) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(
                status.value(),
                exception.getMessage(),
                errors,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(customErrorResponse, status);
    }
}
