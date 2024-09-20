package com.emazon.emazonstockservice.domain.validator;

import com.emazon.emazonstockservice.domain.constants.ErrorMessagesConstants;
import com.emazon.emazonstockservice.domain.constants.PaginationConstants;
import com.emazon.emazonstockservice.domain.exceptions.InvalidParameterPaginationException;
import com.emazon.emazonstockservice.ports.driven.mysql.sortutils.ArticleSortOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaginationValidator {

    private PaginationValidator(){
        throw new IllegalStateException();
    }

    public static void validateArticleSortOptionParameters(String sortBy){

        try {
            ArticleSortOptions.valueOf(sortBy.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterPaginationException(ErrorMessagesConstants.INVALID_SORT_BY_ERROR_MESSAGE + sortBy, Collections.emptyList());
        }
    }
    public static void validatePaginationParameters(Integer pageNumber, Integer pageSize, String sortDirection, String sortBy) {
        List<String> errors = new ArrayList<>();

        if (pageNumber == null || pageNumber < PaginationConstants.MIN_PAGE_NUMBER_VALUE) {
            errors.add(ErrorMessagesConstants.INVALID_PAGE_NO_ERROR_MESSAGE);
        }

        if (pageSize == null || pageSize <= PaginationConstants.MIN_PAGE_SIZE_VALUE) {
            errors.add(ErrorMessagesConstants.INVALID_PAGE_SIZE_ERROR_MESSAGE);
        }

        if (sortDirection == null || (!sortDirection.equalsIgnoreCase(PaginationConstants.SORT_DIRECTION_ASC)
                && !sortDirection.equalsIgnoreCase(PaginationConstants.SORT_DIRECTION_DESC))) {
            errors.add(ErrorMessagesConstants.INVALID_SORT_DIRECTION_ERROR_MESSAGE);
        }

        if (sortBy == null || sortBy.isEmpty()) {
            errors.add(ErrorMessagesConstants.INVALID_SORT_BY_ERROR_MESSAGE);
        }

        if (!errors.isEmpty()) {
            throw new InvalidParameterPaginationException(ErrorMessagesConstants.INVALID_PARAMETERS_ERROR_MESSAGE, errors);
        }
    }

}
