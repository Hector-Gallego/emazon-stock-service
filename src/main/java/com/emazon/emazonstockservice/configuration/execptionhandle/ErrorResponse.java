package com.emazon.emazonstockservice.configuration.execptionhandle;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {

    private Integer status;
    private String message;
    private List<String> errors;
    private LocalDateTime timestamp;
}
