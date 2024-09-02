package com.emazon.emazonstockservice.ports.driven.mapper;


import com.emazon.emazonstockservice.domain.model.Article;
import com.emazon.emazonstockservice.ports.driven.entity.ArticleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleEntityMapper {

    Article toDomain(ArticleEntity entity);
    ArticleEntity toEntity(Article domain);
}
