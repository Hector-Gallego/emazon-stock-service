package com.emazon.emazonstockservice.ports.driving.controller;


import com.emazon.emazonstockservice.domain.api.IArticleServicePort;
import com.emazon.emazonstockservice.domain.model.Article;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.ports.driving.dto.request.ArticleRequestDto;
import com.emazon.emazonstockservice.ports.driving.dto.response.ArticleResponseDto;
import com.emazon.emazonstockservice.ports.driving.dto.response.CustomApiResponse;
import com.emazon.emazonstockservice.ports.driving.dto.response.GenericListResponseDto;
import com.emazon.emazonstockservice.ports.driving.mapper.ArticleRequestMapper;
import com.emazon.emazonstockservice.ports.driving.mapper.ArticleResponseMapper;
import com.emazon.emazonstockservice.ports.driving.mapper.CustomPageMapper;
import com.emazon.emazonstockservice.ports.util.OpenApiConstants;
import com.emazon.emazonstockservice.ports.util.PortsConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/article")
public class ArticleRestController {


    private final IArticleServicePort articleServicePort;
    private final ArticleRequestMapper articleRequestMapper;
    private final ArticleResponseMapper articleResponseMapper;


    public ArticleRestController(IArticleServicePort articleServicePort, ArticleRequestMapper articleRequestMapper, ArticleResponseMapper articleResponseMapper) {
        this.articleServicePort = articleServicePort;
        this.articleRequestMapper = articleRequestMapper;
        this.articleResponseMapper = articleResponseMapper;
    }



    @Operation(summary = OpenApiConstants.OPENAPI_ARTICLE_SUMMARY, description = OpenApiConstants.OPENAPI_ARTICLE_DESCRIPTION)
    @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_201, description = OpenApiConstants.ARTICLE_CREATED)
    @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_400, description = OpenApiConstants.INVALID_INPUT)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = OpenApiConstants.ARTICLE_DATA,
            required = true,
            content = @Content(
                    schema = @Schema(implementation = ArticleRequestDto.class)
            )
    )
    @PostMapping("/")
    public ResponseEntity<CustomApiResponse<Void>> saveArticle(@Validated @RequestBody ArticleRequestDto articleRequestDto) {


        articleServicePort.saveArticle(
                articleRequestMapper.toDomain(articleRequestDto),
                articleRequestDto.getCategoryIds(),
                articleRequestDto.getBrandId());

        CustomApiResponse<Void> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                PortsConstants.ARTICLE_CREATED_SUCCESSFULLY,
                null,
                LocalDateTime.now()
        );

        return ResponseEntity.ok().body(response);

    }


    @GetMapping("/")
    public ResponseEntity<CustomApiResponse<GenericListResponseDto<ArticleResponseDto>>> listArticles(
            @RequestParam int pageNumber,
            @RequestParam int pageSize,
            @RequestParam String sortDirection,
            @RequestParam String sortBy) {

        CustomPage<Article> articlePage = articleServicePort
                .listArticles(pageNumber, pageSize, sortBy, sortDirection);

        GenericListResponseDto<ArticleResponseDto> articleList = CustomPageMapper.convertToDto(articlePage, articleResponseMapper);

        CustomApiResponse<GenericListResponseDto<ArticleResponseDto>> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                PortsConstants.CATEGORIES_RETRIEVED_SUCCESSFULLY,
                articleList,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
