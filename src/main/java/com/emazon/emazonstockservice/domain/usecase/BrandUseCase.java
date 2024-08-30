package com.emazon.emazonstockservice.domain.usecase;

import com.emazon.emazonstockservice.domain.api.IBrandServicePort;
import com.emazon.emazonstockservice.domain.exceptions.DuplicateNameException;
import com.emazon.emazonstockservice.domain.model.Brand;
import com.emazon.emazonstockservice.domain.spi.IBrandPersistencePort;
import com.emazon.emazonstockservice.domain.util.DomainsConstants;
import com.emazon.emazonstockservice.domain.util.FieldValidator;

public class BrandUseCase implements IBrandServicePort {

    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_DESCRIPTION_LENGTH = 120;

    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void saveBrand(Brand brand) {

        FieldValidator.validateNameAndDescription(
                brand.getName(),
                MAX_NAME_LENGTH,
                brand.getDescription(),
                MAX_DESCRIPTION_LENGTH
        );

        if (brandPersistencePort.existsByName(brand.getName())) {
            throw new DuplicateNameException(DomainsConstants.getDuplicateNameFieldMessage(DomainsConstants.BRAND_FIElDS.NAME.toString(), brand.getName()));
        }

        brandPersistencePort.saveBrand(brand);

    }


}
