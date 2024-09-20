package com.emazon.emazonstockservice.ports.driving.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BrandResponseDto {
    private Long id;
    private String name;
    private String description;
}