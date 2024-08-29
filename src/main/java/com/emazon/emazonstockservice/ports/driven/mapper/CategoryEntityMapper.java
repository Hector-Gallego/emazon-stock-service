package com.emazon.emazonstockservice.ports.driven.mapper;


import com.emazon.emazonstockservice.domain.model.Category;

import com.emazon.emazonstockservice.ports.driven.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryEntityMapper {


    CategoryEntity toEntity(Category category);
    Category toDomain(CategoryEntity categoryEntity);


}
