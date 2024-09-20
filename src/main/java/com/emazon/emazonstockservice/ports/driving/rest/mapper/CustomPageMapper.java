package com.emazon.emazonstockservice.ports.driving.rest.mapper;

import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.ports.driving.rest.dto.response.GenericListResponseDto;
import java.util.List;

public class CustomPageMapper {

    private CustomPageMapper(){
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
