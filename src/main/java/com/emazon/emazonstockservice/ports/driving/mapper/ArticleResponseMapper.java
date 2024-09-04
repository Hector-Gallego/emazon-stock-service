package com.emazon.emazonstockservice.ports.driving.mapper;

import com.emazon.emazonstockservice.domain.model.Article;
import com.emazon.emazonstockservice.ports.driving.dto.response.ArticleResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ArticleResponseMapper extends GenericMapper<Article, ArticleResponseDto> {
    Article toDomain(ArticleResponseDto articleResponseDto);
    ArticleResponseDto toDto(Article article);


}
