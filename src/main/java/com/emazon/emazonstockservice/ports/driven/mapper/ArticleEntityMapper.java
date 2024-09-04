package com.emazon.emazonstockservice.ports.driven.mapper;


import com.emazon.emazonstockservice.domain.model.Article;
import com.emazon.emazonstockservice.ports.driven.entity.ArticleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {CategoryEntityMapper.class})
public interface ArticleEntityMapper {

    @Mapping(target = "categories", source = "categoryEntities")
    @Mapping(target = "brand", source = "brandEntity")
    Article toDomain(ArticleEntity entity);
    ArticleEntity toEntity(Article domain);






}
