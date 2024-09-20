package com.emazon.emazonstockservice.ports.driving.rest.mapper;



import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.ports.driving.rest.dto.response.CategoryResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoryResponseMapper extends GenericMapper<Category, CategoryResponseDto> {

    Category toDomain(CategoryResponseDto categoryResponseDto);

}
