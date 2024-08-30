package com.emazon.emazonstockservice.domain.usecase;

import com.emazon.emazonstockservice.domain.exceptions.DuplicateNameException;
import com.emazon.emazonstockservice.domain.exceptions.FieldEmptyException;
import com.emazon.emazonstockservice.domain.exceptions.FieldLimitExceededException;
import com.emazon.emazonstockservice.domain.model.Brand;
import com.emazon.emazonstockservice.domain.spi.IBrandPersistencePort;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.domain.util.DomainsConstants;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class BrandUseCaseTest {

    private IBrandPersistencePort brandPersistencePort;
    private BrandUseCase brandUseCase;
    private Brand brand;

    @BeforeEach
    void setUp() {
        brandPersistencePort = Mockito.mock(IBrandPersistencePort.class);
        brandUseCase = new BrandUseCase(brandPersistencePort);


        brand = new Brand();
        brand.setName("Coca-Cola");
        brand.setDescription("description");
    }

    @Test
    void saveBrand_ShouldSaveBrand_WhenBrandNameIsUnique() {
        // Arrange
        when(brandPersistencePort.existsByName(brand.getName())).thenReturn(false);

        // Act
        brandUseCase.saveBrand(brand);

        // Assert
        verify(brandPersistencePort, times(1)).saveBrand(brand);
    }

    @Test
    void saveBrand_ShouldThrowDuplicateNameException_WhenBrandNameIsNotUnique() {
        // Arrange
        when(brandPersistencePort.existsByName(brand.getName())).thenReturn(true);

        // Act & Assert
        DuplicateNameException exception = assertThrows(DuplicateNameException.class, () -> {
            brandUseCase.saveBrand(brand);
        });

        assertEquals(DomainsConstants.getDuplicateNameFieldMessage(
                        DomainsConstants.BRAND_FIElDS.NAME.toString(),
                        brand.getName()),
                exception.getMessage()
        );
    }


    @Test
    void saveBrand_ShouldThrowValidationException_WhenNameOrDescriptionIsInvalid() {

        brand.setName("");
        // Act & Assert
        FieldEmptyException exception = assertThrows(FieldEmptyException.class, () -> {
            brandUseCase.saveBrand(brand);
        });

        assertEquals(DomainsConstants.NAME_CANNOT_BE_EMPTY, exception.getMessage());


    }

    @Test
    void saveBrand_ShouldThrowFieldLimitExceededException_WhenBrandNameExceedsLimit() {

        brand.setName(StringUtils.repeat('A', DomainsConstants.MAX_BRAND_NAME_LENGTH + 1));
        brand.setDescription("description");

        // Act & Assert
        FieldLimitExceededException exception = assertThrows(FieldLimitExceededException.class, () -> {
            brandUseCase.saveBrand(brand);
        });

        assertEquals(String.format(DomainsConstants.MAX_NAME_LENGTH_MESSAGE, DomainsConstants.MAX_BRAND_NAME_LENGTH), exception.getMessage());
    }

    @Test
    void saveBrand_ShouldThrowFieldLimitExceededException_WhenBrandDescriptionExceedsLimit() {

        brand.setName("name");
        brand.setDescription(StringUtils.repeat('A', DomainsConstants.MAX_BRAND_DESCRIPTION_LENGTH + 1 ));

        // Act & Assert
        FieldLimitExceededException exception = assertThrows(FieldLimitExceededException.class, () -> {
            brandUseCase.saveBrand(brand);
        });

        assertEquals(String.format(DomainsConstants.MAX_DESCRIPTION_LENGTH_MESSAGE, DomainsConstants.MAX_BRAND_DESCRIPTION_LENGTH), exception.getMessage());
    }

    @Test
    void testListBrands() {

        int pageNo = 1;
        int pageSize = 10;
        String sortBy = "name";
        String sortDirection = "asc";

        CustomPage<Brand> expectedPage = new CustomPage.Builder<Brand>()
                .content(new ArrayList<>())
                .pageNumber(pageNo)
                .pageSize(pageSize)
                .totalElements(100L)
                .totalPages(10)
                .first(true)
                .last(false)
                .build();


        when(brandPersistencePort.findAll(pageNo, pageSize, sortBy, sortDirection))
                .thenReturn(expectedPage);


        CustomPage<Brand> result = brandUseCase.listBrands(pageNo, pageSize, sortBy, sortDirection);
        assertEquals(expectedPage, result);
        verify(brandPersistencePort).findAll(pageNo, pageSize, sortBy, sortDirection);
    }

    @Test
    void testListCategoriesWhenDatabaseError() {
        int pageNo = 1;
        int pageSize = 10;
        String sortBy = "name";
        String sortDirection = "asc";


        when(brandPersistencePort.findAll(pageNo, pageSize, sortBy, sortDirection))
                .thenThrow(new RuntimeException("Database error"));


        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            brandUseCase.listBrands(pageNo, pageSize, sortBy, sortDirection);
        });

        assertEquals("Database error", thrownException.getMessage());
    }


}