package com.emazon.emazonstockservice.domain.usecase;

import com.emazon.emazonstockservice.datatest.CategoryDataTestFactory;
import com.emazon.emazonstockservice.datatest.CustomPageDataFactory;
import com.emazon.emazonstockservice.domain.constants.ModelNamesConstants;
import com.emazon.emazonstockservice.domain.exceptions.DuplicateNameException;
import com.emazon.emazonstockservice.domain.exceptions.FieldEmptyException;
import com.emazon.emazonstockservice.domain.exceptions.FieldLimitExceededException;
import com.emazon.emazonstockservice.domain.exceptions.InvalidParameterPaginationException;
import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.spi.CategoryPersistencePort;
import com.emazon.emazonstockservice.domain.constants.CategoryConstants;
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
class CategoryUseCaseTest {


    @Mock
    private CategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;


    @Test
    void ShouldSaveCategoryWhenItDoesNotExist() {


        Category category = CategoryDataTestFactory.createValidCategory();

        when(categoryPersistencePort.existsByName("Tecnología")).thenReturn(false);

        categoryUseCase.saveCategory(category);

        verify(categoryPersistencePort, times(1)).saveCategory(category);

    }

    @Test
    void ShouldThrowExceptionWhenCategoryNameAlreadyExists() {


        Category category = CategoryDataTestFactory.createValidCategory();
        when(categoryPersistencePort.existsByName("Tecnología")).thenReturn(true);

        DuplicateNameException exception = assertThrows(DuplicateNameException.class,
                () -> categoryUseCase.saveCategory(category));

        assertEquals(ErrorMessagesConstants.
                        getDuplicateNameFieldMessage(
                                ModelNamesConstants.CATEGORY.toString(),
                                category.getName()),
                exception.getMessage());

        verify(categoryPersistencePort, times(0)).saveCategory(category);

    }

    @Test
    void ShouldThrowExceptionWhenNameExceedsMaxLength() {

        Category category = CategoryDataTestFactory.createCategoryWhitNameExceedsMaxLength();

        FieldLimitExceededException exception = assertThrows(FieldLimitExceededException.class, () -> categoryUseCase.saveCategory(category));

        assertEquals(String.format(
                ErrorMessagesConstants.MAX_NAME_LENGTH_MESSAGE,
                CategoryConstants.MAX_CATEGORY_NAME_LENGTH), exception.getMessage());


    }

    @Test
    void ShouldThrowExceptionWhenNameIsEmpty() {


        Category category = CategoryDataTestFactory.createCategoryWhitNameEmpty();


        FieldEmptyException exception = assertThrows(FieldEmptyException.class,
                () -> categoryUseCase.saveCategory(category));

        assertEquals(ErrorMessagesConstants.NAME_CANNOT_BE_EMPTY, exception.getMessage());

    }

    @Test
    void ShouldThrowExceptionWhenDescriptionIsEmpty() {

        Category category = CategoryDataTestFactory.createCategoryWhitDescriptionEmpty();

        FieldEmptyException exception = assertThrows(FieldEmptyException.class, () -> categoryUseCase.saveCategory(category));

        assertEquals(ErrorMessagesConstants.DESCRIPTION_CANNOT_BE_EMPTY, exception.getMessage());

    }


    @Test
    void ShouldThrowExceptionWhenDescriptionExceedsMaxLength() {


        Category category = CategoryDataTestFactory.createCategoryWhitDescriptionExceedsMaxLength();

        FieldLimitExceededException exception = assertThrows(
                FieldLimitExceededException.class, () -> categoryUseCase.saveCategory(category));

        assertEquals(String.format(
                ErrorMessagesConstants.MAX_DESCRIPTION_LENGTH_MESSAGE,
                CategoryConstants.MAX_CATEGORY_DESCRIPTION_LENGTH), exception.getMessage());


    }


    @Test
    void ShouldReturnCategoryWhenParametersAreValid() {

        Integer pageNumber = 1;
        Integer pageSize = 10;
        String sortBy = "name";
        String sortDirection = "asc";

        CustomPage<Category> customPage = CustomPageDataFactory.createCustomPageWithParametersAreValid();
        when(categoryPersistencePort.findAll(pageNumber, pageSize, sortBy, sortDirection)).thenReturn(customPage);
        CustomPage<Category> result = categoryUseCase.listCategories(pageNumber, pageSize, sortBy, sortDirection);
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

        assertEquals(ErrorMessagesConstants.INVALID_PARAMETERS_MESSAGE, exception.getMessage());

        List<String> expectedErrors = List.of(
                ErrorMessagesConstants.INVALID_PAGE_NO,
                ErrorMessagesConstants.INVALID_PAGE_SIZE,
                ErrorMessagesConstants.INVALID_SORT_DIRECTION,
                ErrorMessagesConstants.INVALID_SORT_BY

        );

        assertEquals(expectedErrors, exception.getErrors());
    }


}