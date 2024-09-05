package com.emazon.emazonstockservice.domain.usecase;

import com.emazon.emazonstockservice.domain.exceptions.DuplicateNameException;
import com.emazon.emazonstockservice.domain.exceptions.FieldEmptyException;
import com.emazon.emazonstockservice.domain.exceptions.FieldLimitExceededException;
import com.emazon.emazonstockservice.domain.exceptions.InvalidParameterPaginationException;
import com.emazon.emazonstockservice.domain.model.Brand;
import com.emazon.emazonstockservice.domain.spi.IBrandPersistencePort;
import com.emazon.emazonstockservice.domain.util.BrandConstants;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.domain.util.DomainsConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BrandUseCaseTest {


    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    private BrandUseCase brandUseCase;


    @Test
    void ShouldSaveBrandWhenItDoesNotExist() {

        //given
        Brand brand = new Brand(1L, "AstraZeneca", "Famosa por sus medicamentos y tratamientos innovadores.");

        //when
        when(brandPersistencePort.existsByName("AstraZeneca")).thenReturn(false);

        //act
        brandUseCase.saveBrand(brand);

        //then
        verify(brandPersistencePort, times(1)).saveBrand(brand);

    }

    @Test
    void ShouldThrowExceptionWhenBrandNameAlreadyExists() {

        //given
        Brand brand = new Brand(1L, "AstraZeneca", "Famosa por sus medicamentos y tratamientos innovadores.");

        //when
        when(brandPersistencePort.existsByName("AstraZeneca")).thenReturn(true);

        //then
        DuplicateNameException exception = assertThrows(DuplicateNameException.class, () -> brandUseCase.saveBrand(brand));


        assertEquals(DomainsConstants.getDuplicateNameFieldMessage(
                DomainsConstants.MODEL_NAMES.BRAND.toString(),
                brand.getName()), exception.getMessage());


    }

    @Test
    void ShouldThrowExceptionWhenNameExceedsMaxLength() {

        //given
        Brand brand = new Brand(
                1L,
                "a".repeat(BrandConstants.MAX_BRAND_NAME_LENGTH + 1),
                "Reconocida por sus deportivos y autos de alto rendimiento.");

        //then
        FieldLimitExceededException exception = assertThrows(FieldLimitExceededException.class, () -> brandUseCase.saveBrand(brand));

        assertEquals(String.format(
                DomainsConstants.MAX_NAME_LENGTH_MESSAGE,
                BrandConstants.MAX_BRAND_NAME_LENGTH), exception.getMessage());


    }


    @Test
    void ShouldThrowExceptionWhenDescriptionExceedsMaxLength() {

        //given
        Brand brand = new Brand(
                1L,
                "Land Rover",
                "a".repeat(BrandConstants.MAX_BRAND_DESCRIPTION_LENGTH + 1));

        //then
        FieldLimitExceededException exception = assertThrows(
                FieldLimitExceededException.class, () -> brandUseCase.saveBrand(brand));

        assertEquals(String.format(
                DomainsConstants.MAX_DESCRIPTION_LENGTH_MESSAGE,
                BrandConstants.MAX_BRAND_DESCRIPTION_LENGTH), exception.getMessage());


    }


    @Test
    void ShouldThrowExceptionWhenNameIsEmpty() {

        //given
        Brand brand = new Brand(
                1L,
                "",
                "Especialista en vehículos todoterreno y SUVs de lujo.");

        //then
        FieldEmptyException exception = assertThrows(FieldEmptyException.class, () -> brandUseCase.saveBrand(brand));

        assertEquals(DomainsConstants.NAME_CANNOT_BE_EMPTY, exception.getMessage());


    }

    @Test
    void ShouldThrowExceptionWhenDescriptionIsEmpty() {

        //given
        Brand brand = new Brand(
                1L,
                "AstraZeneca",
                "");

        //then
        FieldEmptyException exception = assertThrows(FieldEmptyException.class, () -> brandUseCase.saveBrand(brand));

        assertEquals(DomainsConstants.DESCRIPTION_CANNOT_BE_EMPTY, exception.getMessage());

    }

    @Test
    void shouldThrowExceptionWhenParametersAreInvalid() {
        // given
        Integer pageNumber = -1;
        Integer pageSize = -1;
        String sortBy = "";
        String sortDirection = "ascendente";

        // when & then
        InvalidParameterPaginationException exception = assertThrows(InvalidParameterPaginationException.class, () -> brandUseCase.listBrands(pageNumber, pageSize, sortBy, sortDirection));

        assertEquals(DomainsConstants.INVALID_PARAMETERS_MESSAGE, exception.getMessage());

        List<String> expectedErrors = List.of(
                DomainsConstants.INVALID_PAGE_NO,
                DomainsConstants.INVALID_PAGE_SIZE,
                DomainsConstants.INVALID_SORT_DIRECTION,
                DomainsConstants.INVALID_SORT_BY

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

        CustomPage<Brand> customPage = new CustomPage.Builder<Brand>()
                .content(new ArrayList<>())
                .pageNumber(1)
                .pageSize(10)
                .totalElements(5L)
                .totalPages(5)
                .first(true)
                .last(false)
                .build();

        // when
        when(brandPersistencePort.findAll(pageNumber, pageSize, sortBy, sortDirection)).thenReturn(customPage);
        //act
        CustomPage<Brand> result = brandUseCase.listBrands(pageNumber, pageSize, sortBy, sortDirection);
        // then
        assertEquals(customPage, result);

    }


}