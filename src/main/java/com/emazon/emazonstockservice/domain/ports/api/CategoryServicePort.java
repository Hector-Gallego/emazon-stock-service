package com.emazon.emazonstockservice.domain.ports.api;

import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.util.CustomPage;


public interface CategoryServicePort {
    void saveCategory(Category category);
    CustomPage<Category> listCategories(Integer pageNo, Integer pageSize, String sortBy, String sortDirection);
}
