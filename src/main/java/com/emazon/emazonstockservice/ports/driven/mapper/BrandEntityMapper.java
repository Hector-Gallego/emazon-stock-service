package com.emazon.emazonstockservice.ports.driven.mapper;

import com.emazon.emazonstockservice.domain.model.Brand;
import com.emazon.emazonstockservice.ports.driven.entity.BrandEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface BrandEntityMapper {

    BrandEntity toEntity (Brand brand);
    Brand toDomain (BrandEntity brandEntity);

}
