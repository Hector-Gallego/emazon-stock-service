package com.emazon.emazonstockservice.domain.validator;

import com.emazon.emazonstockservice.domain.constants.ErrorMessagesConstants;
import com.emazon.emazonstockservice.domain.exceptions.InvalidParameterPaginationException;
import com.emazon.emazonstockservice.ports.util.ArticleSortOptions;

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
            throw new InvalidParameterPaginationException(ErrorMessagesConstants.INVALID_SORT_BY + " : " + sortBy, Collections.emptyList());
        }




    }
    public static void validatePaginationParameters(Integer pageNo, Integer pageSize, String sortDirection, String sortBy) {
        List<String> errors = new ArrayList<>();

        if (pageNo == null || pageNo < 0) {
            errors.add(ErrorMessagesConstants.INVALID_PAGE_NO);
        }

        if (pageSize == null || pageSize <= 0) {
            errors.add(ErrorMessagesConstants.INVALID_PAGE_SIZE);
        }

        if (sortDirection == null || (!sortDirection.equalsIgnoreCase("asc") && !sortDirection.equalsIgnoreCase("desc"))) {
            errors.add(ErrorMessagesConstants.INVALID_SORT_DIRECTION);
        }

        if (sortBy == null || sortBy.isEmpty()) {
            errors.add(ErrorMessagesConstants.INVALID_SORT_BY);
        }

        if (!errors.isEmpty()) {
            throw new InvalidParameterPaginationException(ErrorMessagesConstants.INVALID_PARAMETERS_MESSAGE, errors);
        }
    }

}
