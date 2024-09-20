package com.emazon.emazonstockservice.ports.driving.rest.dto.request;


import com.emazon.emazonstockservice.domain.constants.BrandConstants;
import com.emazon.emazonstockservice.domain.constants.CategoryConstants;
import com.emazon.emazonstockservice.domain.constants.ErrorMessagesConstants;
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


    @NotNull(message = ErrorMessagesConstants.NAME_CANNOT_BE_NULL_ERROR_MESSAGE)
    @NotBlank(message = ErrorMessagesConstants.NAME_CANNOT_BE_EMPTY_ERROR_MESSAGE)
    private String name;

    @NotNull(message = ErrorMessagesConstants.DESCRIPTION_CANNOT_BE_NULL_ERROR_MESSAGE)
    @NotBlank(message = ErrorMessagesConstants.DESCRIPTION_CANNOT_BE_EMPTY_ERROR_MESSAGE)
    private String description;

    @NotNull(message = ErrorMessagesConstants.QUANTITY_CANNOT_BE_NULL_ERROR_MESSAGE)
    @PositiveOrZero(message = ErrorMessagesConstants.QUANTITY_MUST_BE_POSITIVE_OR_ZERO_ERROR_MESSAGE)
    private Integer quantity;

    @NotNull(message = ErrorMessagesConstants.PRICE_CANNOT_BE_NULL_ERROR_MESSAGE)
    @DecimalMin(value = "0.0", message = ErrorMessagesConstants.PRICE_MUST_BE_POSITIVE_OR_ZERO_ERROR_MESSAGE)
    private Double price;

    @NotNull(message = CategoryConstants.CATEGORY_IDS_CANNOT_BE_NULL)
    @NotEmpty(message = CategoryConstants.CATEGORY_IDS_CANNOT_BE_EMPTY)
    private List<Long> categoryIds;

    @NotNull(message = BrandConstants.BRAND_ID_CANNOT_BE_NULL)
    private Long brandId;

}
