package com.emazon.emazonstockservice.ports.driving.controller;


import com.emazon.emazonstockservice.configuration.execptionhandle.ErrorResponse;
import com.emazon.emazonstockservice.domain.api.ICategoryServicePort;
import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.util.CategoryConstants;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.ports.driving.dto.request.CategoryRequestDto;
import com.emazon.emazonstockservice.ports.driving.dto.response.CategoryResponseDto;
import com.emazon.emazonstockservice.ports.driving.dto.response.CustomApiResponse;
import com.emazon.emazonstockservice.ports.driving.dto.response.GenericListResponseDto;
import com.emazon.emazonstockservice.ports.driving.mapper.CategoryRequestMapper;
import com.emazon.emazonstockservice.ports.driving.mapper.CategoryResponseMapper;
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
@RequestMapping("/api/category/")
public class CategoryRestController {

    private final ICategoryServicePort categoryServicePort;
    private final CategoryResponseMapper categoryResponseMapper;

    public CategoryRestController(ICategoryServicePort categoryServicePort, CategoryResponseMapper categoryResponseMapper, CategoryRequestMapper categoryRequestMapper) {
        this.categoryServicePort = categoryServicePort;
        this.categoryResponseMapper = categoryResponseMapper;
        this.categoryRequestMapper = categoryRequestMapper;
    }

    private final CategoryRequestMapper categoryRequestMapper;


    @Operation(summary = OpenApiConstants.OPENAPI_CREATE_CATEGORY_SUMMARY,
            description = OpenApiConstants.OPENAPI_CREATE_CATEGORY_DESCRIPTION)

    @ApiResponses(value = {
            @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_201,
                    description = OpenApiConstants.CATEGORY_CREATED,
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
    public ResponseEntity<CustomApiResponse<Void>> saveCategory(@Validated @RequestBody CategoryRequestDto categoryRequestDto) {

        Category category = categoryRequestMapper.toDomain(categoryRequestDto);
        categoryServicePort.saveCategory(category);

        CustomApiResponse<Void> response = new CustomApiResponse<>(
                HttpStatus.CREATED.value(),
                CategoryConstants.CATEGORY_CREATED_SUCCESSFULLY,
                null,
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }


    @Operation(summary = OpenApiConstants.OPENAPI_SUMMARY_LIST_CATEGORIES,
            description = OpenApiConstants.OPENAPI_DESCRIPTION_LIST_CATEGORIES)
    @ApiResponses(value = {

            @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_200,
                    description = OpenApiConstants.OPEN_API_LIST_CATEGORIES_SUCCESS),

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
    public ResponseEntity<CustomApiResponse<GenericListResponseDto<CategoryResponseDto>>> listCategories(
            @RequestParam int pageNumber,
            @RequestParam int pageSize,
            @Parameter(required = true, schema = @Schema(
                    allowableValues = {
                            OpenApiConstants.OPEN_API_ASC_ORDER,
                            OpenApiConstants.OPEN_API_DESC_ORDER}))
            @RequestParam String sortDirection,
            @Parameter(required = true,
                    schema = @Schema(allowableValues = {
                            OpenApiConstants.SORT_BY_NAME}))
            @RequestParam String sortBy) {

        CustomPage<Category> categoryPage = categoryServicePort
                .listCategories(pageNumber, pageSize, sortBy, sortDirection);

        GenericListResponseDto<CategoryResponseDto> categoryList = CustomPageMapper.convertToDto(categoryPage, categoryResponseMapper);

        CustomApiResponse<GenericListResponseDto<CategoryResponseDto>> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                CategoryConstants.CATEGORIES_RETRIEVED_SUCCESSFULLY,
                categoryList,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
