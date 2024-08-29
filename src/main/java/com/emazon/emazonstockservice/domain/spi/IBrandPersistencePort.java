package com.emazon.emazonstockservice.domain.spi;

import com.emazon.emazonstockservice.domain.model.Brand;


public interface IBrandPersistencePort {

    void saveBrand(Brand brand);
    boolean existsByName(String name);

}
