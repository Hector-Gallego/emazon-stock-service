package com.emazon.emazonstockservice.ports.driving.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StockRequestDto {

    private Long articleId;
    private Integer quantity;
}
