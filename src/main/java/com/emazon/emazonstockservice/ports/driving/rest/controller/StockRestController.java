package com.emazon.emazonstockservice.ports.driving.rest.controller;

import com.emazon.emazonstockservice.configuration.exception.execptionhandle.CustomErrorResponse;
import com.emazon.emazonstockservice.configuration.openapi.constants.OpenApiConstants;
import com.emazon.emazonstockservice.configuration.openapi.constants.OpenApiStockConstants;
import com.emazon.emazonstockservice.domain.constants.ArticleConstants;
import com.emazon.emazonstockservice.domain.model.StockVerificationRequest;
import com.emazon.emazonstockservice.domain.model.StockVerificationResponse;
import com.emazon.emazonstockservice.domain.ports.api.StockServicePort;
import com.emazon.emazonstockservice.ports.driving.rest.dto.response.CustomApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/stock")
public class StockRestController {

    private final StockServicePort stockServicePort;

    public StockRestController(StockServicePort stockServicePort) {
        this.stockServicePort = stockServicePort;
    }

    @Operation(summary = OpenApiStockConstants.OPENAPI_UPDATE_STOCK_SUMMARY,
            description = OpenApiStockConstants.OPENAPI_UPDATE_STOCK_DESCRIPTION)

    @ApiResponses(value = {
            @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_200,
                    description = OpenApiStockConstants.STOCK_UPDATED_SUCCESS,
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
    @PutMapping
    public ResponseEntity<CustomApiResponse<Void>> addStock(@RequestBody StockVerificationRequest stockVerificationRequest){

        stockServicePort.addStock(stockVerificationRequest.getArticleId(), stockVerificationRequest.getQuantity());
        CustomApiResponse<Void> response = new CustomApiResponse<>(
                HttpStatus.OK.value(),
                ArticleConstants.STOCK_ADDED_SUCCESS,
                null,
                LocalDateTime.now());
        return ResponseEntity.ok().body(response);

    }

    @Operation(summary = OpenApiStockConstants.OPENAPI_UPDATE_STOCK_SUMMARY,
            description = OpenApiStockConstants.OPENAPI_UPDATE_STOCK_DESCRIPTION)

    @ApiResponses(value = {
            @ApiResponse(responseCode = OpenApiConstants.OPENAPI_CODE_200,
                    description = OpenApiStockConstants.STOCK_VERIFICATION_COMPLETED,
                    content = @Content(mediaType = OpenApiConstants.OPENAPI_MEDIA_TYPE_JSON,
                            schema = @Schema(implementation = StockVerificationResponse.class))),
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
    public ResponseEntity<StockVerificationResponse> verifyStock(@RequestBody StockVerificationRequest stockVerificationRequest){
        StockVerificationResponse response = stockServicePort.checkStockAvailability(stockVerificationRequest);
        return ResponseEntity.ok().body(response);
    }
}
