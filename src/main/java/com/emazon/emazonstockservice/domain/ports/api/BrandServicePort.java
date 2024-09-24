package com.emazon.emazonstockservice.domain.ports.api;

import com.emazon.emazonstockservice.domain.model.Brand;
import com.emazon.emazonstockservice.domain.util.CustomPage;

public interface BrandServicePort {
    void saveBrand(Brand brand);
    CustomPage<Brand> listBrands(Integer pageNo, Integer pageSize, String sortBy, String sortDirection);


}
