package com.emazon.emazonstockservice.ports.driving.rest.dto.response;

import com.emazon.emazonstockservice.domain.model.Brand;
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
        private Set<CategoryListResponseDto> categories;
        private Brand brand;

}
