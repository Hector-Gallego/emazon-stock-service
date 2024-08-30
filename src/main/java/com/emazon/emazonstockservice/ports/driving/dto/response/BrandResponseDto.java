package com.emazon.emazonstockservice.ports.driving.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Schema
public class BrandResponseDto {
    private Long id;
    private String name;
    private String description;
}
