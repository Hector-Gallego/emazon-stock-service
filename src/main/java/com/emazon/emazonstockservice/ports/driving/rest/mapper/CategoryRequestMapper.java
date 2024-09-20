package com.emazon.emazonstockservice.ports.driving.rest.mapper;

import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.ports.driving.rest.dto.request.CategoryRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoryRequestMapper {
    Category toDomain(CategoryRequestDto categoryRequestDto);
}
