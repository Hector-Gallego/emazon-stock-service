package com.emazon.emazonstockservice.ports.driving.rest.controller;

import com.emazon.emazonstockservice.configuration.exception.execptionhandle.CustomErrorResponse;
import com.emazon.emazonstockservice.configuration.openapi.constants.OpenApiBrandConstants;
import com.emazon.emazonstockservice.domain.constants.CategoryConstants;
import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.ports.api.BrandServicePort;
import com.emazon.emazonstockservice.domain.model.Brand;
import com.emazon.emazonstockservice.domain.constants.BrandConstants;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.ports.driving.rest.dto.request.BrandRequestDto;
import com.emazon.emazonstockservice.ports.driving.rest.dto.response.BrandResponseDto;
import com.emazon.emazonstockservice.ports.driving.rest.dto.response.CustomApiResponse;
import com.emazon.emazonstockservice.ports.driving.rest.dto.response.GenericListResponseDto;
import com.emazon.emazonstockservice.ports.driving.rest.mapper.BrandRequestMapper;
import com.emazon.emazonstockservice.ports.driving.rest.mapper.BrandResponseMapper;
import com.emazon.emazonstockservice.ports.driving.rest.mapper.CustomPageMapper;
import com.emazon.emazonstockservice.configuration.openapi.constants.OpenApiConstants;
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
import java.util.List;


@RestController
@RequestMapping("/api/brand")
public class BrandRestController {

    private final BrandServicePort brandServicePort;
    private final BrandResponseMapper brandResponseMapper;
    private final BrandRequestMapper brandRequestMapper;


    public BrandRestController(BrandServicePort brandServicePort, BrandResponseMapper brandResponseMapper, BrandRequestMapper brandRequestMapper) {
        this.brandServicePort = brandServicePort;
        this.brandResponseMapper = brandResponseMapper;
        this.brandRequestMapper = brandRequestMapper;

    }


    @Operation(summary = OpenApiBrandConstants.OPENAPI_CREATE_BRAND_SUMMARY,
            description = OpenApiBrandConstants.OPENAPI_CREATE_BRAND_DESCRIPTION)

    @ApiResponses(value = {
            @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_201,
                    description = OpenApiBrandConstants.BRAND_CREATED,
                    content = @Content(mediaType = OpenApiConstants.OPENAPI_MEDIA_TYPE_JSON,
                            schema = @Schema(implementation = CustomApiResponse.class))),
            @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_400,
                    description = OpenApiConstants.INVALID_INPUT,
                    content = @Content(mediaType = OpenApiConstants.OPENAPI_MEDIA_TYPE_JSON,
                            schema = @Schema(implementation = CustomErrorResponse.class))),
            @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_500,
                    description = OpenApiConstants.OPENAPI_INTERNAL_SERVER_ERROR,
                    content = @Content(mediaType = OpenApiConstants.OPENAPI_MEDIA_TYPE_JSON,
                            schema = @Schema(implementation = CustomErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<CustomApiResponse<Void>> saveBrand(@Validated @RequestBody BrandRequestDto brandRequestDto) {

        Brand brand = brandRequestMapper.toDomain(brandRequestDto);
        brandServicePort.saveBrand(brand);

        CustomApiResponse<Void> response = new CustomApiResponse<>(
                HttpStatus.CREATED.value(),
                BrandConstants.BRAND_CREATED_SUCCESSFULLY,
                null,
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }


    @Operation(summary = OpenApiBrandConstants.OPENAPI_SUMMARY_LIST_BRANDS,
            description = OpenApiBrandConstants.OPENAPI_DESCRIPTION_LIST_BRANDS)
    @ApiResponses(value = {

            @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_200,
                    description = OpenApiBrandConstants.OPEN_API_LIST_BRANDS_SUCCESS),

            @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_400,
                    description = OpenApiConstants.INVALID_INPUT,
                    content = @Content(mediaType = OpenApiConstants.OPENAPI_MEDIA_TYPE_JSON,
                            schema = @Schema(implementation = CustomErrorResponse.class))),

            @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_500,
                    description = OpenApiConstants.OPENAPI_INTERNAL_SERVER_ERROR,
                    content = @Content(mediaType = OpenApiConstants.OPENAPI_MEDIA_TYPE_JSON,
                            schema = @Schema(implementation = CustomErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<CustomApiResponse<GenericListResponseDto<BrandResponseDto>>> listBrands(
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

        CustomPage<Brand> brandPage = brandServicePort
                .listBrands(pageNumber, pageSize, sortBy, sortDirection);

        GenericListResponseDto<BrandResponseDto> brandList = CustomPageMapper.convertToDto(brandPage, brandResponseMapper);

        CustomApiResponse<GenericListResponseDto<BrandResponseDto>> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                BrandConstants.BRANDS_RETRIEVED_SUCCESSFULLY,
                brandList,
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = OpenApiBrandConstants.OPENAPI_SUMMARY_LIST_BRANDS,
            description = OpenApiBrandConstants.OPENAPI_DESCRIPTION_LIST_BRANDS)
    @ApiResponses(value = {

            @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_200,
                    description = OpenApiBrandConstants.OPEN_API_LIST_BRANDS_SUCCESS),

            @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_400,
                    description = OpenApiConstants.INVALID_INPUT,
                    content = @Content(mediaType = OpenApiConstants.OPENAPI_MEDIA_TYPE_JSON,
                            schema = @Schema(implementation = CustomErrorResponse.class))),

            @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_500,
                    description = OpenApiConstants.OPENAPI_INTERNAL_SERVER_ERROR,
                    content = @Content(mediaType = OpenApiConstants.OPENAPI_MEDIA_TYPE_JSON,
                            schema = @Schema(implementation = CustomErrorResponse.class)))
    })
    @GetMapping("/all")
    public ResponseEntity<CustomApiResponse<List<Brand>>> getAllBrands() {

        List<Brand> categoriesList = brandServicePort.getAllBrands();
        CustomApiResponse<List<Brand>> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                BrandConstants.BRANDS_RETRIEVED_SUCCESSFULLY,
                categoriesList,
                LocalDateTime.now()

        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
