package com.emazon.emazonstockservice.ports.driving.mapper;

import com.emazon.emazonstockservice.domain.model.Article;
import com.emazon.emazonstockservice.ports.driving.dto.request.ArticleRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ArticleRequestMapper {

    Article toDomain(ArticleRequestDto articleRequestDto);
    ArticleRequestDto toDto(Article article);
}
