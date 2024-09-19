package com.emazon.emazonstockservice.ports.driving.controller;


import com.emazon.emazonstockservice.configuration.execptionhandle.ErrorResponse;
import com.emazon.emazonstockservice.domain.api.ArticleServicePort;
import com.emazon.emazonstockservice.domain.model.Article;
import com.emazon.emazonstockservice.domain.constants.ArticleConstants;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.ports.driving.dto.request.ArticleRequestDto;
import com.emazon.emazonstockservice.ports.driving.dto.request.StockRequestDto;
import com.emazon.emazonstockservice.ports.driving.dto.response.ArticleResponseDto;
import com.emazon.emazonstockservice.ports.driving.dto.response.CustomApiResponse;
import com.emazon.emazonstockservice.ports.driving.dto.response.GenericListResponseDto;
import com.emazon.emazonstockservice.ports.driving.mapper.ArticleRequestMapper;
import com.emazon.emazonstockservice.ports.driving.mapper.ArticleResponseMapper;
import com.emazon.emazonstockservice.ports.driving.mapper.CustomPageMapper;
import com.emazon.emazonstockservice.ports.util.OpenApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/article")
public class ArticleRestController {


    private final ArticleServicePort articleServicePort;
    private final ArticleRequestMapper articleRequestMapper;
    private final ArticleResponseMapper articleResponseMapper;



    public ArticleRestController(ArticleServicePort articleServicePort, ArticleRequestMapper articleRequestMapper, ArticleResponseMapper articleResponseMapper) {
        this.articleServicePort = articleServicePort;
        this.articleRequestMapper = articleRequestMapper;
        this.articleResponseMapper = articleResponseMapper;

    }


    @Operation(summary = OpenApiConstants.OPENAPI_CREATE_ARTICLE_SUMMARY,
            description = OpenApiConstants.OPENAPI_CREATE_ARTICLE_DESCRIPTION)

    @ApiResponses(value = {
            @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_201,
                    description = OpenApiConstants.ARTICLE_CREATED,
                    content = @Content(mediaType = OpenApiConstants.OPENAPI_MEDIA_TYPE_JSON,
                            schema = @Schema(implementation = CustomApiResponse.class))),
            @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_400,
                    description = OpenApiConstants.INVALID_INPUT,
                    content = @Content(mediaType = OpenApiConstants.OPENAPI_MEDIA_TYPE_JSON,
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_500,
                    description = OpenApiConstants.OPENAPI_INTERNAL_SERVER_ERROR,
                    content = @Content(mediaType = OpenApiConstants.OPENAPI_MEDIA_TYPE_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<CustomApiResponse<Void>> saveArticle(@Validated @RequestBody ArticleRequestDto articleRequestDto) {


        articleServicePort.saveArticle(
                articleRequestMapper.toDomain(articleRequestDto),
                articleRequestDto.getCategoryIds(),
                articleRequestDto.getBrandId());

        CustomApiResponse<Void> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                ArticleConstants.ARTICLE_CREATED_SUCCESSFULLY,
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = OpenApiConstants.OPENAPI_SUMMARY_LIST_ARTICLES,
            description = OpenApiConstants.OPENAPI_DESCRIPTION_LIST_ARTICLES)
    @ApiResponses(value = {

            @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_200,
                    description = OpenApiConstants.OPEN_API_LIST_ARTICLES_SUCCESS),

            @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_400,
                    description = OpenApiConstants.INVALID_INPUT,
                    content = @Content(mediaType = OpenApiConstants.OPENAPI_MEDIA_TYPE_JSON,
                            schema = @Schema(implementation = ErrorResponse.class))),

            @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_500,
                    description = OpenApiConstants.OPENAPI_INTERNAL_SERVER_ERROR,
                    content = @Content(mediaType = OpenApiConstants.OPENAPI_MEDIA_TYPE_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<CustomApiResponse<GenericListResponseDto<ArticleResponseDto>>> listArticles(
            @RequestParam int pageNumber,
            @RequestParam int pageSize,
            @Parameter(required = true, schema = @Schema(
                    allowableValues = {
                            OpenApiConstants.OPEN_API_ASC_ORDER,
                            OpenApiConstants.OPEN_API_DESC_ORDER}))
            @RequestParam String sortDirection,
            @Parameter(required = true,
                    schema = @Schema(allowableValues = {
                            OpenApiConstants.SORT_BY_NAME,
                            OpenApiConstants.SORT_BY_BRAND_NAME,
                            OpenApiConstants.SORT_BY_CATEGORY_NAME}))
            @RequestParam String sortBy) {

        CustomPage<Article> articlePage = articleServicePort
                .listArticles(pageNumber, pageSize, sortBy, sortDirection);

        GenericListResponseDto<ArticleResponseDto> articleList = CustomPageMapper.convertToDto(articlePage, articleResponseMapper);

        CustomApiResponse<GenericListResponseDto<ArticleResponseDto>> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                ArticleConstants.ARTICLES_RETRIEVED_SUCCESSFULLY,
                articleList,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @Operation(summary = OpenApiConstants.OPENAPI_UPDATE_STOCK_SUMMARY,
            description = OpenApiConstants.OPENAPI_UPDATE_STOCK_DESCRIPTION)

    @ApiResponses(value = {
            @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_201,
                    description = OpenApiConstants.STOCK_UPDATED,
                    content = @Content(mediaType = OpenApiConstants.OPENAPI_MEDIA_TYPE_JSON,
                            schema = @Schema(implementation = CustomApiResponse.class))),
            @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_400,
                    description = OpenApiConstants.INVALID_INPUT,
                    content = @Content(mediaType = OpenApiConstants.OPENAPI_MEDIA_TYPE_JSON,
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_500,
                    description = OpenApiConstants.OPENAPI_INTERNAL_SERVER_ERROR,
                    content = @Content(mediaType = OpenApiConstants.OPENAPI_MEDIA_TYPE_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/stock")
    public ResponseEntity<CustomApiResponse<Void>> addStock(@RequestBody StockRequestDto stockRequestDto){

        articleServicePort.addStock(stockRequestDto.getArticleId(), stockRequestDto.getQuantity());
        CustomApiResponse<Void> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                "Stock added successfully",
                null,
                LocalDateTime.now());
        return ResponseEntity.ok().body(response);

    }
}
