package com.emazon.emazonstockservice.domain.spi;

import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.util.CustomPage;

public interface ICategoryPersistencePort {
    void saveCategory(Category category);
    boolean existsByName(String name);
    CustomPage<Category> findAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
}
