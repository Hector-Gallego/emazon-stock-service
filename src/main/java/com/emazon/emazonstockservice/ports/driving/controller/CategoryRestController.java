package com.emazon.emazonstockservice.ports.driving.controller;



import com.emazon.emazonstockservice.domain.api.ICategoryServicePort;
import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.ports.driving.dto.request.CategoryRequestDto;
import com.emazon.emazonstockservice.ports.driving.mapper.CategoryResponseMapper;
import com.emazon.emazonstockservice.ports.util.OpenApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/category")
public class CategoryRestController {

    private final ICategoryServicePort categoryServicePort;
    private final CategoryResponseMapper categoryMapper;


    public CategoryRestController(ICategoryServicePort categoryServicePort,
                                  CategoryResponseMapper categoryMapper) {

        this.categoryServicePort = categoryServicePort;
        this.categoryMapper = categoryMapper;
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




}
