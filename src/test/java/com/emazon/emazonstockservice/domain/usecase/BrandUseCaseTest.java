package com.emazon.emazonstockservice.domain.usecase;

import com.emazon.emazonstockservice.datatest.BrandDataTestFactory;
import com.emazon.emazonstockservice.datatest.CustomPageDataFactory;
import com.emazon.emazonstockservice.domain.constants.ModelNamesConstants;
import com.emazon.emazonstockservice.domain.exceptions.DuplicateNameException;
import com.emazon.emazonstockservice.domain.exceptions.FieldEmptyException;
import com.emazon.emazonstockservice.domain.exceptions.FieldLimitExceededException;
import com.emazon.emazonstockservice.domain.exceptions.InvalidParameterPaginationException;
import com.emazon.emazonstockservice.domain.model.Brand;
import com.emazon.emazonstockservice.domain.ports.spi.BrandPersistencePort;
import com.emazon.emazonstockservice.domain.constants.BrandConstants;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.domain.constants.ErrorMessagesConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BrandUseCaseTest {


    @Mock
    private BrandPersistencePort brandPersistencePort;

    @InjectMocks
    private BrandUseCase brandUseCase;


    @Test
    void ShouldSaveBrandWhenItDoesNotExist() {


        Brand brand = BrandDataTestFactory.createValidBrand();

        when(brandPersistencePort.existsByName("AstraZeneca")).thenReturn(false);

        brandUseCase.saveBrand(brand);

        verify(brandPersistencePort, times(1)).saveBrand(brand);

    }

    @Test
    void ShouldThrowExceptionWhenBrandNameAlreadyExists() {


        Brand brand = BrandDataTestFactory.createValidBrand();

        when(brandPersistencePort.existsByName("AstraZeneca")).thenReturn(true);

        DuplicateNameException exception = assertThrows(DuplicateNameException.class, () -> brandUseCase.saveBrand(brand));

        assertEquals(ErrorMessagesConstants.getDuplicateNameFieldMessage(
                ModelNamesConstants.BRAND.toString(),
                brand.getName()), exception.getMessage());


    }

    @Test
    void ShouldThrowExceptionWhenNameExceedsMaxLength() {


        Brand brand = BrandDataTestFactory.createBrandWhitNameExceedsMaxLength();
        FieldLimitExceededException exception = assertThrows(FieldLimitExceededException.class, () -> brandUseCase.saveBrand(brand));

        assertEquals(String.format(
                ErrorMessagesConstants.MAX_NAME_LENGTH_ERROR_MESSAGE,
                BrandConstants.MAX_BRAND_NAME_LENGTH), exception.getMessage());

    }


    @Test
    void ShouldThrowExceptionWhenDescriptionExceedsMaxLength() {

        Brand brand = BrandDataTestFactory.createBrandWhitDescriptionExceedsMaxLength();

        FieldLimitExceededException exception = assertThrows(
                FieldLimitExceededException.class, () -> brandUseCase.saveBrand(brand));

        assertEquals(String.format(
                ErrorMessagesConstants.MAX_DESCRIPTION_LENGTH_ERROR_MESSAGE,
                BrandConstants.MAX_BRAND_DESCRIPTION_LENGTH), exception.getMessage());


    }


    @Test
    void ShouldThrowExceptionWhenNameIsEmpty() {

        Brand brand = BrandDataTestFactory.createBrandWhitNameEmpty();

        FieldEmptyException exception = assertThrows(FieldEmptyException.class, () -> brandUseCase.saveBrand(brand));

        assertEquals(ErrorMessagesConstants.NAME_CANNOT_BE_EMPTY_ERROR_MESSAGE, exception.getMessage());

    }

    @Test
    void ShouldThrowExceptionWhenDescriptionIsEmpty() {


        Brand brand = BrandDataTestFactory.createBrandWhitDescriptionEmpty();
        FieldEmptyException exception = assertThrows(FieldEmptyException.class, () -> brandUseCase.saveBrand(brand));
        assertEquals(ErrorMessagesConstants.DESCRIPTION_CANNOT_BE_EMPTY_ERROR_MESSAGE, exception.getMessage());

    }

    @Test
    void shouldThrowExceptionWhenParametersAreInvalid() {

        Integer pageNumber = -1;
        Integer pageSize = -1;
        String sortBy = "";
        String sortDirection = "ascendente";
        InvalidParameterPaginationException exception = assertThrows(InvalidParameterPaginationException.class, () -> brandUseCase.listBrands(pageNumber, pageSize, sortBy, sortDirection));

        assertEquals(ErrorMessagesConstants.INVALID_PARAMETERS_ERROR_MESSAGE, exception.getMessage());

        List<String> expectedErrors = List.of(
                ErrorMessagesConstants.INVALID_PAGE_NO_ERROR_MESSAGE,
                ErrorMessagesConstants.INVALID_PAGE_SIZE_ERROR_MESSAGE,
                ErrorMessagesConstants.INVALID_SORT_DIRECTION_ERROR_MESSAGE,
                ErrorMessagesConstants.INVALID_SORT_BY_ERROR_MESSAGE

        );

        assertEquals(expectedErrors, exception.getErrors());
    }

    @Test
    void ShouldReturnBrandsWhenParametersAreValid() {
        // given
        Integer pageNumber = 1;
        Integer pageSize = 10;
        String sortBy = "name";
        String sortDirection = "asc";

        CustomPage<Brand> customPage = CustomPageDataFactory.createCustomPageWithParametersAreValid();

        when(brandPersistencePort.findAll(pageNumber, pageSize, sortBy, sortDirection)).thenReturn(customPage);

        CustomPage<Brand> result = brandUseCase.listBrands(pageNumber, pageSize, sortBy, sortDirection);

        assertEquals(customPage, result);

    }


}