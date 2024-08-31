package com.emazon.emazonstockservice.ports.driving.dto.request;

import com.emazon.emazonstockservice.domain.util.BrandConstants;
import com.emazon.emazonstockservice.domain.util.DomainsConstants;
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
public class BrandRequestDto {

    @NotNull(message = PortsConstants.NAME_CANNOT_BE_NULL)
    @NotBlank(message = PortsConstants.NAME_CANNOT_BE_EMPTY)
    @Size(max = PortsConstants.MAX_BRAND_NAME_LENGTH, message = PortsConstants.MAX_BRAND_NAME_LENGTH_MESSAGE)
    private String name;

    @NotNull(message = DomainsConstants.DESCRIPTION_CANNOT_BE_NULL)
    @NotBlank(message = DomainsConstants.DESCRIPTION_CANNOT_BE_EMPTY)
    @Size(max = BrandConstants.MAX_BRAND_DESCRIPTION_LENGTH, message = PortsConstants.MAX_BRAND_DESCRIPTION_LENGTH_MESSAGE)
    private String description;
}
