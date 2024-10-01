package com.emazon.emazonstockservice.ports.driving.rest.dto.response;

import com.emazon.emazonstockservice.domain.model.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ArticleCartResponseDto {

    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private Double price;
    private Set<CategoryListResponseDto> categories;
    private Brand brand;
    private boolean isSufficientStock;
    private LocalDate supplyDate;
}
