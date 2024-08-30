package com.emazon.emazonstockservice.ports.driving.controller;

import com.emazon.emazonstockservice.domain.api.IBrandServicePort;
import com.emazon.emazonstockservice.domain.model.Brand;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.ports.driving.dto.request.BrandRequestDto;
import com.emazon.emazonstockservice.ports.driving.dto.response.BrandResponseDto;
import com.emazon.emazonstockservice.ports.driving.dto.response.CustomApiResponse;
import com.emazon.emazonstockservice.ports.driving.dto.response.GenericListResponseDto;
import com.emazon.emazonstockservice.ports.driving.mapper.BrandRequestMapper;
import com.emazon.emazonstockservice.ports.driving.mapper.BrandResponseMapper;
import com.emazon.emazonstockservice.ports.util.PageConverter;
import com.emazon.emazonstockservice.ports.util.OpenApiConstants;
import com.emazon.emazonstockservice.ports.util.PortsConstants;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/brand")
public class BrandRestController {

    private final IBrandServicePort brandServicePort;
    private final BrandResponseMapper brandResponseMapper;
    private final BrandRequestMapper brandRequestMapper;


    public BrandRestController(IBrandServicePort brandServicePort, BrandResponseMapper brandResponseMapper, BrandRequestMapper brandRequestMapper) {
        this.brandServicePort = brandServicePort;
        this.brandResponseMapper = brandResponseMapper;
        this.brandRequestMapper = brandRequestMapper;

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
    public ResponseEntity<CustomApiResponse<Void>> saveBrand(@Validated @RequestBody BrandRequestDto brandRequestDto){

        Brand brand = brandRequestMapper.toDomain(brandRequestDto);
        brandServicePort.saveBrand(brand);

        CustomApiResponse<Void> response = new CustomApiResponse<>(
                HttpStatus.CREATED.value(),
                PortsConstants.BRAND_CREATED_SUCCESSFULLY,
                null,
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/")
    @Operation(summary = OpenApiConstants.OPENAPI_SUMMARY_LIST_BRANDS, description = OpenApiConstants.OPENAPI_DESCRIPTION_LIST_BRANDS)
    @ApiResponses(value = {
            @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_200, description = OpenApiConstants.OPEN_API_LIST_BRANDS_SUCCESS,
                    content = @Content(mediaType = OpenApiConstants.OPENAPI_MEDIA_TYPE_JSON,
                            schema = @Schema(implementation = CustomApiResponse.class))),
            @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_400, description = OpenApiConstants.OPEN_API_INVALID_PARAMETERS,
                    content = @Content(mediaType = OpenApiConstants.OPENAPI_MEDIA_TYPE_JSON)),
            @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_500, description = OpenApiConstants.OPENAPI_INTERNAL_SERVER_ERROR,
                    content = @Content(mediaType = OpenApiConstants.OPENAPI_MEDIA_TYPE_JSON))
    })
    public ResponseEntity<CustomApiResponse<GenericListResponseDto<BrandResponseDto>>> listBrands(
            @RequestParam int pageNo,
            @RequestParam int pageSize,
            @RequestParam String sortDirection,
            @RequestParam String sortBy) {

        CustomPage<Brand> brandPage = brandServicePort
                .listBrands(pageNo, pageSize, sortBy, sortDirection);

        GenericListResponseDto<BrandResponseDto> brandList = PageConverter.convertToDto(brandPage, brandResponseMapper);

        CustomApiResponse<GenericListResponseDto<BrandResponseDto>> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                PortsConstants.CATEGORIES_RETRIEVED_SUCCESSFULLY,
                brandList,
                LocalDateTime.now()
        );

        return ResponseEntity.ok(response);
    }




}
