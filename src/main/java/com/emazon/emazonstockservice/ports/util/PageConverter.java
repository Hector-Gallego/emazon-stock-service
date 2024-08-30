package com.emazon.emazonstockservice.ports.util;

import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.ports.driving.dto.response.GenericListResponseDto;
import com.emazon.emazonstockservice.ports.driving.mapper.GenericMapper;

import java.util.List;

public class PageConverter {

    private PageConverter(){
        throw new IllegalStateException();
    }

    public static <T, D> GenericListResponseDto<D> convertToDto(
            CustomPage<T> customPage,
            GenericMapper<T, D> mapper
    ) {
        List<D> dtoList = customPage.getContent().stream()
                .map(mapper::toDto)
                .toList();

        return new GenericListResponseDto<>(
                dtoList,
                customPage.getPageNumber(),
                customPage.getPageSize(),
                customPage.getTotalElements(),
                customPage.getTotalPages(),
                customPage.isLast(),
                customPage.isFirst()
        );
    }
}
