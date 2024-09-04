package com.emazon.emazonstockservice.ports.driven.mapper;

import com.emazon.emazonstockservice.domain.model.Brand;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.ports.driven.entity.BrandEntity;
import org.springframework.data.domain.Page;

public class BrandPageMapper {

    public BrandPageMapper(BrandEntityMapper brandEntityMapper) {
        this.brandEntityMapper = brandEntityMapper;
    }

    private final BrandEntityMapper brandEntityMapper;

    public CustomPage<Brand> toCustomPage(Page<BrandEntity> page) {
        CustomPageMapper<BrandEntity, Brand> mapper = new CustomPageMapper<>(brandEntityMapper::toDomain);
        return mapper.toCustomPage(page);
    }


}
