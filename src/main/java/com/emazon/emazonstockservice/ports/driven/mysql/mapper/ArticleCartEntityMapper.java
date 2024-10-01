package com.emazon.emazonstockservice.ports.driven.mysql.mapper;

import com.emazon.emazonstockservice.domain.model.stock.ArticleCart;
import com.emazon.emazonstockservice.ports.driven.mysql.entity.ArticleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {CategoryEntityMapper.class})
public interface ArticleCartEntityMapper {

    @Mapping(target = "categories", source = "categoryEntities")
    @Mapping(target = "brand", source = "brandEntity")
    ArticleCart toDomain(ArticleEntity entity);
    ArticleEntity toEntity(ArticleCart domain);

}
