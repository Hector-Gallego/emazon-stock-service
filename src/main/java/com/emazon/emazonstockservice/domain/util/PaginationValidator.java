package com.emazon.emazonstockservice.domain.util;

public class PaginationValidator {

    private PaginationValidator(){
        throw  new IllegalStateException();
    }

    private static final int DEFAULT_PAGE_NO = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final String DEFAULT_SORT_DIRECTION = "asc";
    private static final String DEFAULT_SORT_BY = "name";

    public static int validatePageNo(Integer pageNo) {
        return (pageNo == null || pageNo < 0) ? DEFAULT_PAGE_NO : pageNo;
    }

    public static int validatePageSize(Integer pageSize) {
        return (pageSize == null || pageSize <= 0) ? DEFAULT_PAGE_SIZE : pageSize;
    }

    public static String validateSortDirection(String sortDirection) {
        if (sortDirection == null || (!sortDirection.equalsIgnoreCase("asc") && !sortDirection.equalsIgnoreCase("desc"))) {
            return DEFAULT_SORT_DIRECTION;
        }
        return sortDirection.toLowerCase();
    }

    public static String validateSortBy(String sortBy) {
        return (sortBy == null || sortBy.isEmpty()) ? DEFAULT_SORT_BY : sortBy;
    }

}
