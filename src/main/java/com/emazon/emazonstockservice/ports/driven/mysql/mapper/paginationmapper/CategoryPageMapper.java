package com.emazon.emazonstockservice.ports.driven.mysql.mapper.paginationmapper;

import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.ports.driven.mysql.entity.CategoryEntity;
import com.emazon.emazonstockservice.ports.driven.mysql.mapper.CategoryEntityMapper;
import org.springframework.data.domain.Page;

public class CategoryPageMapper {

    private final CategoryEntityMapper categoryMapper;

    public CategoryPageMapper(CategoryEntityMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public CustomPage<Category> toCustomPage(Page<CategoryEntity> page) {
        CustomPageMapper<CategoryEntity, Category> mapper = new CustomPageMapper<>(categoryMapper::toDomain);
        return mapper.toCustomPage(page);
    }
}
