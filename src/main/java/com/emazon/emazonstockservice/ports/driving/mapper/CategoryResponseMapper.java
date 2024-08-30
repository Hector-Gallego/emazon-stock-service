package com.emazon.emazonstockservice.ports.driving.mapper;



import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.ports.driving.dto.request.CategoryRequestDto;
import com.emazon.emazonstockservice.ports.driving.dto.response.CategoryResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoryResponseMapper extends GenericMapper<Category, CategoryResponseDto> {

    Category toDomain(CategoryResponseDto categoryResponseDto);
    CategoryResponseDto ToDto (Category category);

}
