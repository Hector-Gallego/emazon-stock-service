package com.emazon.emazonstockservice.domain.spi;

import com.emazon.emazonstockservice.domain.model.Brand;
import com.emazon.emazonstockservice.domain.util.CustomPage;


public interface IBrandPersistencePort {

    void saveBrand(Brand brand);
    boolean existsByName(String name);
    CustomPage<Brand> findAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
}
