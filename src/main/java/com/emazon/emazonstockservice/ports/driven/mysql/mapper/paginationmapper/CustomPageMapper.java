package com.emazon.emazonstockservice.ports.driven.mysql.mapper.paginationmapper;

import com.emazon.emazonstockservice.domain.util.CustomPage;
import org.springframework.data.domain.Page;

import java.util.function.Function;

public class CustomPageMapper <E, D>{

    private final Function<E, D> entityToDomainConverter;

    public CustomPageMapper(Function<E, D> entityToDomainConverter) {
        this.entityToDomainConverter = entityToDomainConverter;
    }

    public CustomPage<D> toCustomPage(Page<E> page) {
        return new CustomPage.Builder<D>()
                .content(page.getContent().stream()
                        .map(entityToDomainConverter)
                        .toList())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }
}
