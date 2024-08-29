package com.emazon.emazonstockservice.ports.driven.mapper;

import com.emazon.emazonstockservice.domain.model.Brand;
import com.emazon.emazonstockservice.ports.driven.entity.BrandEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandEntityMapper {

    BrandEntity toEntity (Brand brand);
    Brand toDomain (BrandEntity brandEntity);
}
