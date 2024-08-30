package com.emazon.emazonstockservice.ports.driving.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
public class CustomApiResponse<T>{

    private int statusCode;
    private String message;
    private T data;
    private LocalDateTime timestamp;
}
