package com.emazon.emazonstockservice.domain.usecase;
import com.emazon.emazonstockservice.domain.exceptions.DuplicateNameException;
import com.emazon.emazonstockservice.domain.exceptions.FieldEmptyException;
import com.emazon.emazonstockservice.domain.model.Brand;
import com.emazon.emazonstockservice.domain.spi.IBrandPersistencePort;
import com.emazon.emazonstockservice.domain.util.DomainsConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class BrandUseCaseTest {

    private IBrandPersistencePort brandPersistencePort;
    private BrandUseCase brandUseCase;

    @BeforeEach
    void setUp() {
        brandPersistencePort = Mockito.mock(IBrandPersistencePort.class);
        brandUseCase = new BrandUseCase(brandPersistencePort);
    }

    @Test
    void saveBrand_ShouldThrowDuplicateNameException_WhenBrandNameIsNotUnique() {
        // Arrange
        Brand brand = new Brand();
        brand.setName("Coca-Cola");
        brand.setDescription("description");

        when(brandPersistencePort.existsByName(brand.getName())).thenReturn(true);

        // Act & Assert
        DuplicateNameException exception = assertThrows(DuplicateNameException.class, () -> {
            brandUseCase.saveBrand(brand);
        });

        assertEquals(
                DomainsConstants.getDuplicateNameFieldMessage(DomainsConstants.BRAND_FIElDS.NAME.toString(), brand.getName()),
                exception.getMessage()
        );
    }

    @Test
    void saveBrand_ShouldSaveBrand_WhenBrandNameIsUnique() {
        // Arrange
        Brand brand = new Brand();
        brand.setName("Coca-Cola");
        brand.setDescription("description");

        when(brandPersistencePort.existsByName(brand.getName())).thenReturn(false);

        // Act
        brandUseCase.saveBrand(brand);

        // Assert
        verify(brandPersistencePort, times(1)).saveBrand(brand);
    }

    @Test
    void saveBrand_ShouldThrowValidationException_WhenNameOrDescriptionIsInvalid() {
        // Arrange
        Brand brand = new Brand();
        brand.setName(""); // Nombre inválido
        brand.setDescription("description");


        // Act & Assert
        FieldEmptyException exception = assertThrows(FieldEmptyException.class, () -> {
            brandUseCase.saveBrand(brand);
        });

        assertEquals("Name cannot be empty.", exception.getMessage());
    }

}