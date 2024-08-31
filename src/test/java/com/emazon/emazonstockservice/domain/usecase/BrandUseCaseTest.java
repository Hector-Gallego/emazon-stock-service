package com.emazon.emazonstockservice.domain.usecase;

import com.emazon.emazonstockservice.domain.exceptions.DuplicateNameException;
import com.emazon.emazonstockservice.domain.exceptions.FieldEmptyException;
import com.emazon.emazonstockservice.domain.exceptions.FieldLimitExceededException;
import com.emazon.emazonstockservice.domain.model.Brand;
import com.emazon.emazonstockservice.domain.spi.IBrandPersistencePort;
import com.emazon.emazonstockservice.domain.util.BrandConstants;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.domain.util.DomainsConstants;

import com.emazon.emazonstockservice.domain.util.PaginationValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        Brand brand = new Brand(1L, "brand_1", "description_1");

        //when
        when(brandPersistencePort.existsByName("brand_1")).thenReturn(false);

        //act
        brandUseCase.saveBrand(brand);

        //then
        verify(brandPersistencePort, times(1)).saveBrand(brand);

    }

    @Test
    void ShouldThrowExceptionWhenBrandNameAlreadyExists() {

        //given
        Brand brand = new Brand(1L, "brand_1", "description_1");

        //when
        when(brandPersistencePort.existsByName("brand_1")).thenReturn(true);

        //then
        DuplicateNameException exception = assertThrows(DuplicateNameException.class, () -> {
            brandUseCase.saveBrand(brand);
        });


        assertEquals(DomainsConstants.getDuplicateNameFieldMessage(
                        DomainsConstants.MODEL_NAMES.BRAND.toString(),
                        brand.getName()), exception.getMessage());

        verify(brandPersistencePort, times(0)).saveBrand(brand);

    }

    @Test
    void ShouldThrowExceptionWhenNameExceedsMaxLength() {

        //given
        Brand brand = new Brand(
                1L,
                "a".repeat(BrandConstants.MAX_BRAND_NAME_LENGTH + 1),
                "description_1");

        //then
        FieldLimitExceededException exception = assertThrows(FieldLimitExceededException.class, () -> {
            brandUseCase.saveBrand(brand);
        });

        assertEquals(String.format(
                DomainsConstants.MAX_NAME_LENGTH_MESSAGE,
                BrandConstants.MAX_BRAND_NAME_LENGTH), exception.getMessage());


        verify(brandPersistencePort, times(0)).saveBrand(brand);

    }


    @Test
    void ShouldThrowExceptionWhenDescriptionExceedsMaxLength() {

        //given
        Brand brand = new Brand(
                1L,
                "name_1",
                "a".repeat(BrandConstants.MAX_BRAND_DESCRIPTION_LENGTH + 1));

        //then
        FieldLimitExceededException exception = assertThrows(
                FieldLimitExceededException.class, () -> {
                    brandUseCase.saveBrand(brand);
                });

        assertEquals(String.format(
                DomainsConstants.MAX_DESCRIPTION_LENGTH_MESSAGE,
                BrandConstants.MAX_BRAND_DESCRIPTION_LENGTH), exception.getMessage());


        verify(brandPersistencePort, times(0)).saveBrand(brand);

    }

    @Test
    void ShouldReturnBrandsWhenParametersAreValid() {

        // given
        Integer pageNo = 1;
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
        when(brandPersistencePort.findAll(pageNo, pageSize, sortBy, sortDirection)).thenReturn(customPage);

        //act
        CustomPage<Brand> result = brandUseCase.listBrands(pageNo, pageSize, sortBy, sortDirection);

        // then
        assertEquals(customPage, result);
        verify(brandPersistencePort, times(1)).findAll(pageNo, pageSize, sortBy, sortDirection);
    }

    @Test
    void ShouldThrowExceptionWhenNameIsEmpty() {

        //given
        Brand brand = new Brand(
                1L,
                "",
                "description_1");

        //then
        FieldEmptyException exception = assertThrows(FieldEmptyException.class, () -> {
            brandUseCase.saveBrand(brand);
        });

        assertEquals(DomainsConstants.NAME_CANNOT_BE_EMPTY, exception.getMessage());

        verify(brandPersistencePort, times(0)).saveBrand(brand);

    }

    @Test
    void ShouldThrowExceptionWhenDescriptionIsEmpty() {

        //given
        Brand brand = new Brand(
                1L,
                "name_1",
                "");

        //then
        FieldEmptyException exception = assertThrows(FieldEmptyException.class, () -> {
            brandUseCase.saveBrand(brand);
        });

        assertEquals(DomainsConstants.DESCRIPTION_CANNOT_BE_EMPTY, exception.getMessage());

        verify(brandPersistencePort, times(0)).saveBrand(brand);

    }

    @Test
    void ShouldReturnBrandsWhenParametersAreInvalid() {
        // given
        Integer pageNo = null;
        Integer pageSize = -1;
        String sortBy = "";
        String sortDirection = "ascendente";

        CustomPage<Brand> customPage = new CustomPage.Builder<Brand>()
                .content(Collections.singletonList(new Brand(1L, "brand_1", "description_1")))
                .pageNumber(PaginationValidator.DEFAULT_PAGE_NO)
                .pageSize(PaginationValidator.DEFAULT_PAGE_SIZE)
                .totalElements(1L)
                .totalPages(1)
                .first(true)
                .last(true)
                .build();

        when(brandPersistencePort.findAll(
                PaginationValidator.DEFAULT_PAGE_NO,
                PaginationValidator.DEFAULT_PAGE_SIZE,
                PaginationValidator.DEFAULT_SORT_BY,
                PaginationValidator.DEFAULT_SORT_DIRECTION
        )).thenReturn(customPage);

        // act
        CustomPage<Brand> result = brandUseCase.listBrands(pageNo, pageSize, sortBy, sortDirection);

        // then
        assertEquals(customPage, result);

        verify(brandPersistencePort, times(1)).findAll(
                PaginationValidator.DEFAULT_PAGE_NO,
                PaginationValidator.DEFAULT_PAGE_SIZE,
                PaginationValidator.DEFAULT_SORT_BY,
                PaginationValidator.DEFAULT_SORT_DIRECTION
        );
    }


    @Test
    void ShouldReturnBrandsInAscendingOrder() {
        // given
        Integer pageNo = 0;
        Integer pageSize = 10;
        String sortBy = "name";
        String sortDirection = "asc";


        List<Brand> brands = Arrays.asList(
                new Brand(1L, "A_Brand", "description_1"),
                new Brand(2L, "B_Brand", "description_2"),
                new Brand(3L, "C_Brand", "description_3")
        );

        CustomPage<Brand> customPage = new CustomPage.Builder<Brand>()
                .content(brands)
                .pageNumber(pageNo)
                .pageSize(pageSize)
                .totalElements((long) brands.size())
                .totalPages(1)
                .first(true)
                .last(true)
                .build();

        when(brandPersistencePort.findAll(
                pageNo,
                pageSize,
                sortBy,
                sortDirection
        )).thenReturn(customPage);

        // act
        CustomPage<Brand> result = brandUseCase.listBrands(pageNo, pageSize, sortBy, sortDirection);

        // then
        assertEquals(customPage, result);

        verify(brandPersistencePort, times(1)).findAll(
                pageNo,
                pageSize,
                sortBy,
                sortDirection
        );


    }



    @Test
    void ShouldReturnBrandsInDescendingOrder() {
        // given
        Integer pageNo = 0;
        Integer pageSize = 10;
        String sortBy = "name";
        String sortDirection = "desc";


        List<Brand> brands = Arrays.asList(
                new Brand(1L, "C_Brand", "description_1"),
                new Brand(2L, "B_Brand", "description_2"),
                new Brand(3L, "A_Brand", "description_3")
        );

        CustomPage<Brand> customPage = new CustomPage.Builder<Brand>()
                .content(brands)
                .pageNumber(pageNo)
                .pageSize(pageSize)
                .totalElements((long) brands.size())
                .totalPages(1)
                .first(true)
                .last(true)
                .build();

        when(brandPersistencePort.findAll(
                pageNo,
                pageSize,
                sortBy,
                sortDirection
        )).thenReturn(customPage);

        // act
        CustomPage<Brand> result = brandUseCase.listBrands(pageNo, pageSize, sortBy, sortDirection);

        // then
        assertEquals(customPage, result);

        verify(brandPersistencePort, times(1)).findAll(
                pageNo,
                pageSize,
                sortBy,
                sortDirection
        );


    }


}