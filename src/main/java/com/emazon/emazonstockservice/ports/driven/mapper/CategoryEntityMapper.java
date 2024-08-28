package com.emazon.emazonstockservice.ports.driven.mapper;



import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.ports.driven.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface CategoryEntityMapper {


    CategoryEntity toEntity(Category category);
    Category toDomain(CategoryEntity categoryEntity);

    // Método para convertir Page<CategoryEntity> a CustomPage<Category>
    default CustomPage<Category> toCustomPage(Page<CategoryEntity> page) {

        return new CustomPage.Builder<Category>()
                .content(page.getContent().stream()
                        .map(this::toDomain)
                        .toList())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }
}
