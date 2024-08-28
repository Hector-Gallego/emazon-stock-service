package com.emazon.emazonstockservice.ports.driving.mapper;


import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.ports.driving.dto.response.GenericListResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface GenericListResponseMapper {

    /**
     * otra forma:
     * GenericListResponseDto<Category> toCategoryDto(CustomPage<Category> customPage);
     */


    default <T> GenericListResponseDto<T> toDto(CustomPage<T> customPage) {

        GenericListResponseDto<T> responseDto = new GenericListResponseDto<>();
        responseDto.setContent(customPage.getContent());
        responseDto.setPageNumber(customPage.getPageNumber());
        responseDto.setPageSize(customPage.getPageSize());
        responseDto.setTotalElements(customPage.getTotalElements());
        responseDto.setTotalPages(customPage.getTotalPages());
        responseDto.setLast(customPage.isLast());
        responseDto.setFirst(customPage.isFirst());
        return responseDto;
    }
}


