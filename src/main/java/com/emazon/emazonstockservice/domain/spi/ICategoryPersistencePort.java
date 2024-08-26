package com.emazon.emazonstockservice.domain.spi;

import com.emazon.emazonstockservice.domain.model.Category;

public interface ICategoryPersistencePort {
    void saveCategory(Category category);
    boolean existsByName(String name);
}
