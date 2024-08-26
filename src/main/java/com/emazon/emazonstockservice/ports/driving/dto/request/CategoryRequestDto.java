package com.emazon.emazonstockservice.ports.driving.dto.request;


import com.emazon.emazonstockservice.ports.util.PortsConstants;
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


    @NotNull(message = PortsConstants.NAME_CANNOT_BE_NULL)
    @NotBlank(message = PortsConstants.NAME_CANNOT_BE_EMPTY)
    @Size(max = 50, message = PortsConstants.MAX_NAME_LENGTH)
    private String name;

    @NotNull(message = PortsConstants.DESCRIPTION_CANNOT_BE_NULL)
    @NotBlank(message = PortsConstants.DESCRIPTION_CANNOT_BE_EMPTY)
    @Size(max = 90, message = PortsConstants.MAX_DESCRIPTION_LENGTH)
    private String description;
}
