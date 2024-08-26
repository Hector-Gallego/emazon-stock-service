package com.emazon.emazonstockservice.configuration.execptionhandle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@Setter
@Getter
public class ErrorResponse {

    private LocalDate timestamp;
    private String message;
    private HttpStatus status;
    private List<String> errors;
}
