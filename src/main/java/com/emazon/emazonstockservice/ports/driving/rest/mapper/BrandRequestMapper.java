package com.emazon.emazonstockservice.ports.driving.rest.mapper;


import com.emazon.emazonstockservice.domain.model.Brand;
import com.emazon.emazonstockservice.ports.driving.rest.dto.request.BrandRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface BrandRequestMapper {
    Brand toDomain(BrandRequestDto brandRequestDto);
}

