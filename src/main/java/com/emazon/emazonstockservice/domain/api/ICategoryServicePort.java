package com.emazon.emazonstockservice.domain.api;

import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.util.CustomPage;


public interface ICategoryServicePort {
    void saveCategory(Category category);
    CustomPage<Category> listCategories(int pageNo, int pageSize, String sortBy, String sortDirection);
}
