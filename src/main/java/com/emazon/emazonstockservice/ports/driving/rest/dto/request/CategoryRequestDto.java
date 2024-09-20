package com.emazon.emazonstockservice.ports.driving.rest.dto.request;


import com.emazon.emazonstockservice.domain.constants.CategoryConstants;
import com.emazon.emazonstockservice.domain.constants.ErrorMessagesConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestDto {


    @NotNull(message = ErrorMessagesConstants.NAME_CANNOT_BE_NULL_ERROR_MESSAGE)
    @NotBlank(message = ErrorMessagesConstants.NAME_CANNOT_BE_EMPTY_ERROR_MESSAGE)
    @Size(max = CategoryConstants.MAX_CATEGORY_NAME_LENGTH, message = CategoryConstants.MAX_CATEGORY_NAME_LENGTH_MESSAGE)
    private String name;

    @NotNull(message = ErrorMessagesConstants.DESCRIPTION_CANNOT_BE_NULL_ERROR_MESSAGE)
    @NotBlank(message = ErrorMessagesConstants.DESCRIPTION_CANNOT_BE_EMPTY_ERROR_MESSAGE)
    @Size(max = CategoryConstants.MAX_CATEGORY_DESCRIPTION_LENGTH, message = CategoryConstants.MAX_CATEGORY_DESCRIPTION_LENGTH_MESSAGE)
    private String description;
}
