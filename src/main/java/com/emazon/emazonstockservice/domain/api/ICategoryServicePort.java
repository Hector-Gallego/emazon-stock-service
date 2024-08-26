package com.emazon.emazonstockservice.domain.api;

import com.emazon.emazonstockservice.domain.model.Category;

public interface ICategoryServicePort {
    void saveCategory(Category category);
}
