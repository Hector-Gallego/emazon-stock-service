package com.emazon.emazonstockservice.ports.driving.controller;



import com.emazon.emazonstockservice.domain.api.ICategoryServicePort;
import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.ports.driving.dto.request.CategoryRequestDto;
import com.emazon.emazonstockservice.ports.driving.dto.response.GenericListResponseDto;
import com.emazon.emazonstockservice.ports.driving.mapper.GenericListResponseMapper;
import com.emazon.emazonstockservice.ports.driving.mapper.CategoryResponseMapper;
import com.emazon.emazonstockservice.ports.util.OpenApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/category")
public class CategoryRestController {

    private final ICategoryServicePort categoryServicePort;
    private final CategoryResponseMapper categoryMapper;
    private final GenericListResponseMapper genericListResponseMapper;


    public CategoryRestController(ICategoryServicePort categoryServicePort,
                                  CategoryResponseMapper categoryMapper,
                                  GenericListResponseMapper genericListResponseMapper) {

        this.categoryServicePort = categoryServicePort;
        this.categoryMapper = categoryMapper;
        this.genericListResponseMapper = genericListResponseMapper;
    }

    @Operation(summary = OpenApiConstants.OPENAPI_SUMMARY, description = OpenApiConstants.OPENAPI_DESCRIPTION)
    @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_201, description = OpenApiConstants.CATEGORY_CREATED)
    @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_400, description = OpenApiConstants.INVALID_INPUT)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = OpenApiConstants.CATEGORY_DATA,
            required = true,
            content = @Content(
                    schema = @Schema(implementation = CategoryRequestDto.class)
            )
    )
    @PostMapping("/")
    public ResponseEntity<Void> saveCategory(@Validated @RequestBody CategoryRequestDto categoryRequestDto){

        Category category = categoryMapper.toDto(categoryRequestDto);
        categoryServicePort.saveCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }



    @GetMapping("/")
    @Operation(summary = OpenApiConstants.OPENAPI_SUMMARY_LIST_CATEGORIES, description = OpenApiConstants.OPENAPI_DESCRIPTION_LIST_CATEGORIES)
    @ApiResponses(value = {
            @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_200, description = OpenApiConstants.OPEN_API_LIST_CATEGORIES_SUCCESS,
                    content = @Content(mediaType = OpenApiConstants.OPENAPI_MEDIA_TYPE_JSON,
                            schema = @Schema(implementation = GenericListResponseDto.class))),
            @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_400, description = OpenApiConstants.OPEN_API_INVALID_PARAMETERS,
                    content = @Content(mediaType = OpenApiConstants.OPENAPI_MEDIA_TYPE_JSON)),
            @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_500, description = OpenApiConstants.OPENAPI_INTERNAL_SERVER_ERROR,
                    content = @Content(mediaType = OpenApiConstants.OPENAPI_MEDIA_TYPE_JSON))
    })
    public ResponseEntity<GenericListResponseDto<Category>> listCategories(
            @RequestParam int pageNo,
            @RequestParam int pageSize,
            @RequestParam String sortDirection,
            @RequestParam String sortBy){

        CustomPage<Category> categoryPage = categoryServicePort
                .listCategories(pageNo, pageSize, sortBy, sortDirection);

        GenericListResponseDto<Category> responseDto = genericListResponseMapper.toDto(categoryPage);
        return ResponseEntity.ok(responseDto);
    }


}
