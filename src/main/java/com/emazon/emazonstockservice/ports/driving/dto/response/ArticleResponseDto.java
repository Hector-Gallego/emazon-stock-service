package com.emazon.emazonstockservice.ports.driving.dto.response;

import com.emazon.emazonstockservice.domain.model.Brand;
import com.emazon.emazonstockservice.domain.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ArticleResponseDto {

    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private Double price;
    private Set<Category> categories;
    private Brand brand;

}
