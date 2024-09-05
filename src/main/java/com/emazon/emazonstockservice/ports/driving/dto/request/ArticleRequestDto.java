package com.emazon.emazonstockservice.ports.driving.dto.request;


import com.emazon.emazonstockservice.domain.util.BrandConstants;
import com.emazon.emazonstockservice.domain.util.CategoryConstants;
import com.emazon.emazonstockservice.ports.util.PortsConstants;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ArticleRequestDto {


    @NotNull(message = PortsConstants.NAME_CANNOT_BE_NULL)
    @NotBlank(message = PortsConstants.NAME_CANNOT_BE_EMPTY)
    private String name;

    @NotNull(message = PortsConstants.DESCRIPTION_CANNOT_BE_NULL)
    @NotBlank(message = PortsConstants.DESCRIPTION_CANNOT_BE_EMPTY)
    private String description;

    @NotNull(message = PortsConstants.QUANTITY_CANNOT_BE_NULL)
    @PositiveOrZero(message = PortsConstants.QUANTITY_MUST_BE_POSITIVE_OR_ZERO)
    private Integer quantity;

    @NotNull(message = PortsConstants.PRICE_CANNOT_BE_NULL)
    @DecimalMin(value = "0.0", message = PortsConstants.PRICE_MUST_BE_POSITIVE_OR_ZERO)
    private Double price;

    @NotNull(message = CategoryConstants.CATEGORY_IDS_CANNOT_BE_NULL)
    @NotEmpty(message = CategoryConstants.CATEGORY_IDS_CANNOT_BE_EMPTY)
    private List<Long> categoryIds;

    @NotNull(message = BrandConstants.BRAND_ID_CANNOT_BE_NULL)
    private Long brandId;

}
