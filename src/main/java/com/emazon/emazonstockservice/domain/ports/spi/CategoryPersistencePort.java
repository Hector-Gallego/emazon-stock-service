package com.emazon.emazonstockservice.domain.ports.spi;

import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.util.CustomPage;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CategoryPersistencePort {

    Map<Long, Optional<Category>> findCategoryByIds(List<Long> categoryIds);
    void saveCategory(Category category);
    boolean existsByName(String name);
    CustomPage<Category> findAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
    List<Category> getAllCategories();
}
