package com.emazon.emazonstockservice.domain.usecase;

import com.emazon.emazonstockservice.domain.api.IBrandServicePort;
import com.emazon.emazonstockservice.domain.exceptions.DuplicateNameException;
import com.emazon.emazonstockservice.domain.model.Brand;
import com.emazon.emazonstockservice.domain.spi.IBrandPersistencePort;
import com.emazon.emazonstockservice.domain.util.*;

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
            throw new DuplicateNameException(DomainsConstants.getDuplicateNameFieldMessage(DomainsConstants.MODEL_NAMES.BRAND.toString(), brand.getName()));
        }

        brandPersistencePort.saveBrand(brand);

    }

    @Override
    public CustomPage<Brand> listBrands(Integer pageNo, Integer pageSize, String sortBy, String sortDirection) {

        PaginationValidator.validatePaginationParameters(pageNo,pageSize,sortDirection,sortBy);
        return  brandPersistencePort.findAll(pageNo, pageSize, sortBy, sortDirection);

    }
}
