package com.emazon.emazonstockservice.datatest;

import com.emazon.emazonstockservice.domain.util.CustomPage;

import java.util.ArrayList;

public class CustomPageDataFactory {

    public static<T> CustomPage<T> createCustomPageWithParametersAreValid() {
        return new CustomPage.Builder<T>()
                .content(new ArrayList<T>())
                .pageNumber(1)
                .pageSize(10)
                .totalElements(5L)
                .totalPages(5)
                .first(true)
                .last(false)
                .build();
    }
}
