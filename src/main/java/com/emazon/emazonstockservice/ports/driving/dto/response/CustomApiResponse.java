package com.emazon.emazonstockservice.ports.driving.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
public class CustomApiResponse<T>{

    private HttpStatus statusCode;
    private String message;
    private T data;
    private LocalDateTime timestamp;
}
