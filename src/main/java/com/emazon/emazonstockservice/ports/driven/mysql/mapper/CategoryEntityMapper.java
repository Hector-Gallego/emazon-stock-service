package com.emazon.emazonstockservice.ports.driven.mysql.mapper;


import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.ports.driven.mysql.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoryEntityMapper {


    CategoryEntity toEntity(Category category);
    Category toDomain(CategoryEntity categoryEntity);




}
