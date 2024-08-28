package com.emazon.emazonstockservice.domain.spi;

import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoryPersistencePort {
    void saveCategory(Category category);
    boolean existsByName(String name);
    CustomPage<Category> findAll(int pageNo, int pageSize, String sortBy, String sortDirection);
}
