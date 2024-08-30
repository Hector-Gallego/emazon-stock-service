package com.emazon.emazonstockservice.ports.driving.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
@Schema(description = "Custom API Response with metadata and data.")
public class CustomApiResponse<T>{
    @Schema
    private int statusCode;
    @Schema
    private String message;
    @Schema
    private T data;
    @Schema
    private LocalDateTime timestamp;
}
