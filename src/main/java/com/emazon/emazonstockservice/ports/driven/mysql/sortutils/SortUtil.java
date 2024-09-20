package com.emazon.emazonstockservice.ports.driven.mysql.sortutils;

import org.springframework.data.domain.Sort;



public final class SortUtil {

    private SortUtil(){
        throw new IllegalStateException();
    }

    public static Sort getSort(ISortOptions sortOption, String sortDirection) {

        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        String sortField = sortOption.getField();
        return Sort.by(direction, sortField);
    }
}
