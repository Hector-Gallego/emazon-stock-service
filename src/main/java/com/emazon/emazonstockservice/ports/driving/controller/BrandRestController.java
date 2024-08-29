package com.emazon.emazonstockservice.ports.driving.controller;

import com.emazon.emazonstockservice.domain.api.IBrandServicePort;
import com.emazon.emazonstockservice.domain.model.Brand;
import com.emazon.emazonstockservice.ports.driving.dto.request.BrandRequestDto;
import com.emazon.emazonstockservice.ports.driving.mapper.BrandResponseMapper;
import com.emazon.emazonstockservice.ports.util.OpenApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/brand")
public class BrandRestController {

    private final IBrandServicePort brandServicePort;
    private final BrandResponseMapper brandResponseMapper;

    public BrandRestController(IBrandServicePort brandServicePort, BrandResponseMapper brandResponseMapper) {
        this.brandServicePort = brandServicePort;
        this.brandResponseMapper = brandResponseMapper;
    }

    @PostMapping("/")
    @Operation(summary = OpenApiConstants.OPENAPI_BRAND_SUMMARY, description = OpenApiConstants.OPENAPI_BRAND_DESCRIPTION)
    @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_201, description = OpenApiConstants.BRAND_CREATED)
    @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_400, description = OpenApiConstants.INVALID_INPUT)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = OpenApiConstants.BRAND_DATA,
            required = true,
            content = @Content(
                    schema = @Schema(implementation = BrandRequestDto.class)
            )
    )
    public ResponseEntity<Void> saveBrand(@Validated @RequestBody BrandRequestDto brandRequestDto){

        Brand brand = brandResponseMapper.toDto(brandRequestDto);
        brandServicePort.saveBrand(brand);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }



}
