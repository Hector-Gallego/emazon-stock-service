package com.emazon.emazonstockservice.domain.usecase;

import com.emazon.emazonstockservice.domain.ports.api.BrandServicePort;
import com.emazon.emazonstockservice.domain.constants.BrandConstants;
import com.emazon.emazonstockservice.domain.constants.ErrorMessagesConstants;
import com.emazon.emazonstockservice.domain.constants.ModelNamesConstants;
import com.emazon.emazonstockservice.domain.exceptions.DuplicateNameException;
import com.emazon.emazonstockservice.domain.model.Brand;
import com.emazon.emazonstockservice.domain.ports.spi.BrandPersistencePort;
import com.emazon.emazonstockservice.domain.util.*;
import com.emazon.emazonstockservice.domain.validator.FieldValidator;
import com.emazon.emazonstockservice.domain.validator.PaginationValidator;

import java.util.List;

public class BrandUseCase implements BrandServicePort {


    private final BrandPersistencePort brandPersistencePort;

    public BrandUseCase(BrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void saveBrand(Brand brand) {

        FieldValidator.validateNameAndDescription(
                brand.getName(),
                BrandConstants.MAX_BRAND_NAME_LENGTH,
                brand.getDescription(),
                BrandConstants.MAX_BRAND_DESCRIPTION_LENGTH
        );

        if (brandPersistencePort.existsByName(brand.getName())) {
            throw new DuplicateNameException(ErrorMessagesConstants.getDuplicateNameFieldMessage(ModelNamesConstants.BRAND.getSpanishName(), brand.getName()));
        }

        brandPersistencePort.saveBrand(brand);

    }

    @Override
    public CustomPage<Brand> listBrands(Integer pageNo, Integer pageSize, String sortBy, String sortDirection) {

        PaginationValidator.validatePaginationParameters(pageNo,pageSize,sortDirection,sortBy);
        return  brandPersistencePort.findAll(pageNo, pageSize, sortBy, sortDirection);

    }

    @Override
    public List<Brand> getAllBrands() {
        return brandPersistencePort.getAllBrands();
    }
}
