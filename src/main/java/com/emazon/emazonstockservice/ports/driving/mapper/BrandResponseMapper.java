package com.emazon.emazonstockservice.ports.driving.mapper;

import com.emazon.emazonstockservice.domain.model.Brand;
import com.emazon.emazonstockservice.ports.driving.dto.response.BrandResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface BrandResponseMapper extends GenericMapper<Brand, BrandResponseDto> {
    Brand toDomain(BrandResponseDto brandResponseDto);
    BrandResponseDto toDto(Brand brand);
}
