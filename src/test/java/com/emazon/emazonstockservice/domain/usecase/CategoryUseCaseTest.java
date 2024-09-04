package com.emazon.emazonstockservice.domain.usecase;

import com.emazon.emazonstockservice.domain.exceptions.DuplicateNameException;
import com.emazon.emazonstockservice.domain.exceptions.FieldEmptyException;
import com.emazon.emazonstockservice.domain.exceptions.FieldLimitExceededException;
import com.emazon.emazonstockservice.domain.exceptions.InvalidParameterPaginationException;
import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.spi.ICategoryPersistencePort;
import com.emazon.emazonstockservice.domain.util.CategoryConstants;
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
class CategoryUseCaseTest {


    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;


    @Test
    void ShouldSaveCategoryWhenItDoesNotExist() {

        //given
        Category category = new Category(1L, "Tecnología", "Gadgets y dispositivos electrónicos como teléfonos y computadoras.");

        //when
        when(categoryPersistencePort.existsByName("Tecnología")).thenReturn(false);

        //act
        categoryUseCase.saveCategory(category);

        //then
        verify(categoryPersistencePort, times(1)).saveCategory(category);

    }

    @Test
    void ShouldThrowExceptionWhenCategoryNameAlreadyExists() {

        //given
        Category category = new Category(1L, "Tecnología", "Gadgets y dispositivos electrónicos como teléfonos y computadoras.");

        //when
        when(categoryPersistencePort.existsByName("Tecnología")).thenReturn(true);

        //then
        DuplicateNameException exception = assertThrows(DuplicateNameException.class,
                () -> categoryUseCase.saveCategory(category));


        assertEquals(DomainsConstants.
                        getDuplicateNameFieldMessage(
                                DomainsConstants.MODEL_NAMES.CATEGORY.toString(),
                                category.getName()),
                exception.getMessage());

        verify(categoryPersistencePort, times(0)).saveCategory(category);

    }

    @Test
    void ShouldThrowExceptionWhenNameExceedsMaxLength() {

        //given
        Category category = new Category(
                1L,
                "a".repeat(CategoryConstants.MAX_CATEGORY_NAME_LENGTH + 1),
                "Ropa, calzado y equipo para actividades físicas.");

        //then
        FieldLimitExceededException exception = assertThrows(FieldLimitExceededException.class, () -> categoryUseCase.saveCategory(category));

        assertEquals(String.format(
                DomainsConstants.MAX_NAME_LENGTH_MESSAGE,
                CategoryConstants.MAX_CATEGORY_NAME_LENGTH), exception.getMessage());


    }

    @Test
    void ShouldThrowExceptionWhenNameIsEmpty() {

        //given
        Category category = new Category(
                1L,
                "",
                "Ropa, calzado y equipo para actividades físicas");

        //then
        FieldEmptyException exception = assertThrows(FieldEmptyException.class,
                () -> categoryUseCase.saveCategory(category));

        assertEquals(DomainsConstants.NAME_CANNOT_BE_EMPTY, exception.getMessage());

    }

    @Test
    void ShouldThrowExceptionWhenDescriptionIsEmpty() {

        //given
        Category category = new Category(
                1L,
                "Juguetes",
                "");

        //then
        FieldEmptyException exception = assertThrows(FieldEmptyException.class, () -> categoryUseCase.saveCategory(category));

        assertEquals(DomainsConstants.DESCRIPTION_CANNOT_BE_EMPTY, exception.getMessage());


    }


    @Test
    void ShouldThrowExceptionWhenDescriptionExceedsMaxLength() {

        //given
        Category category = new Category(
                1L,
                "Muebles",
                "a".repeat(CategoryConstants.MAX_CATEGORY_DESCRIPTION_LENGTH + 1));

        //then
        FieldLimitExceededException exception = assertThrows(
                FieldLimitExceededException.class, () -> categoryUseCase.saveCategory(category));

        assertEquals(String.format(
                DomainsConstants.MAX_DESCRIPTION_LENGTH_MESSAGE,
                CategoryConstants.MAX_CATEGORY_DESCRIPTION_LENGTH), exception.getMessage());


    }


    @Test
    void ShouldReturnCategoryWhenParametersAreValid() {
        // given
        Integer pageNumber = 1;
        Integer pageSize = 10;
        String sortBy = "name";
        String sortDirection = "asc";

        CustomPage<Category> customPage = new CustomPage.Builder<Category>()
                .content(new ArrayList<>())
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalElements(5L)
                .totalPages(5)
                .first(true)
                .last(false)
                .build();

        // when
        when(categoryPersistencePort.findAll(pageNumber, pageSize, sortBy, sortDirection)).thenReturn(customPage);

        // act
        CustomPage<Category> result = categoryUseCase.listCategories(pageNumber, pageSize, sortBy, sortDirection);

        // then
        assertEquals(customPage, result);
    }

    @Test
    void ShouldReturnCategoriesWhenParametersAreInvalid() {
        // given
        Integer pageNumber = -10;
        Integer pageSize = -1;
        String sortBy = "";
        String sortDirection = "ascendente";

        // when & then
        InvalidParameterPaginationException exception = assertThrows(InvalidParameterPaginationException.class, () -> categoryUseCase.listCategories(pageNumber, pageSize, sortBy, sortDirection));

        assertEquals(DomainsConstants.INVALID_PARAMETERS_MESSAGE, exception.getMessage());

        List<String> expectedErrors = List.of(
                DomainsConstants.INVALID_PAGE_NO,
                DomainsConstants.INVALID_PAGE_SIZE,
                DomainsConstants.INVALID_SORT_DIRECTION,
                DomainsConstants.INVALID_SORT_BY

        );

        assertEquals(expectedErrors, exception.getErrors());
    }


}