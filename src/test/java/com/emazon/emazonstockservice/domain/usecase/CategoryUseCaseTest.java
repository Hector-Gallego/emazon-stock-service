package com.emazon.emazonstockservice.domain.usecase;

import com.emazon.emazonstockservice.domain.exceptions.DuplicateNameException;
import com.emazon.emazonstockservice.domain.exceptions.FieldEmptyException;
import com.emazon.emazonstockservice.domain.exceptions.FieldLimitExceededException;
import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.spi.ICategoryPersistencePort;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.domain.util.DomainsConstants;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryUseCaseTest {

    private ICategoryPersistencePort categoryPersistencePort;
    private CategoryUseCase categoryUsecase;
    private Category category;

    @BeforeEach
    void setUp() {
        categoryPersistencePort = Mockito.mock(ICategoryPersistencePort.class);
        categoryUsecase = new CategoryUseCase(categoryPersistencePort);

        category = new Category();
        category.setName("Electronics");
        category.setDescription("description");


    }

    @Test
    void saveCategory_ShouldSaveCategory_WhenCategoryNameIsUnique() {
        // Arrange
        when(categoryPersistencePort.existsByName(category.getName())).thenReturn(false);

        // Act
        categoryUsecase.saveCategory(category);

        // Assert
        verify(categoryPersistencePort, times(1)).saveCategory(category);
    }

    @Test
    void saveCategory_ShouldThrowDuplicateNameException_WhenCategoryNameIsNotUnique() {
        // Arrange
        when(categoryPersistencePort.existsByName(category.getName())).thenReturn(true);

        // Act & Assert
        DuplicateNameException exception = assertThrows(DuplicateNameException.class, () -> {
            categoryUsecase.saveCategory(category);
        });

        assertEquals(DomainsConstants.getDuplicateNameFieldMessage(
                        DomainsConstants.CATEGORY_FIELDS.NAME.toString(),
                        category.getName()),
                exception.getMessage());
    }



    @Test
    void saveCategory_ShouldThrowValidationException_WhenNameOrDescriptionIsInvalid() {

        category.setName("");
        // Act & Assert
        FieldEmptyException exception = assertThrows(FieldEmptyException.class, () -> {
            categoryUsecase.saveCategory(category);
        });

        assertEquals(DomainsConstants.NAME_CANNOT_BE_EMPTY, exception.getMessage());
    }

    @Test
    void saveCategory_ShouldThrowFieldLimitExceededException_WhenCategoryNameExceedsLimit() {

        category.setName(StringUtils.repeat('A', DomainsConstants.MAX_CATEGORY_NAME_LENGTH + 1));
        category.setDescription("description");

        // Act & Assert
        FieldLimitExceededException exception = assertThrows(FieldLimitExceededException.class, () -> {
            categoryUsecase.saveCategory(category);
        });

        assertEquals(String.format(DomainsConstants.MAX_NAME_LENGTH_MESSAGE, DomainsConstants.MAX_CATEGORY_NAME_LENGTH), exception.getMessage());
    }

    @Test
    void saveCategory_ShouldThrowFieldLimitExceededException_WhenCategoryDescriptionExceedsLimit() {

        category.setName("name");
        category.setDescription(StringUtils.repeat('A',DomainsConstants.MAX_CATEGORY_DESCRIPTION_LENGTH + 1));

        // Act & Assert
        FieldLimitExceededException exception = assertThrows(FieldLimitExceededException.class, () -> {
            categoryUsecase.saveCategory(category);
        });

        assertEquals(String.format(DomainsConstants.MAX_DESCRIPTION_LENGTH_MESSAGE, DomainsConstants.MAX_CATEGORY_DESCRIPTION_LENGTH), exception.getMessage());
    }


    @Test
    void testListCategories() {

        int pageNo = 1;
        int pageSize = 10;
        String sortBy = "name";
        String sortDirection = "asc";

        CustomPage<Category> expectedPage = new CustomPage.Builder<Category>()
                .content(new ArrayList<>())
                .pageNumber(pageNo)
                .pageSize(pageSize)
                .totalElements(100L)
                .totalPages(10)
                .first(true)
                .last(false)
                .build();


        when(categoryPersistencePort.findAll(pageNo, pageSize, sortBy, sortDirection))
                .thenReturn(expectedPage);


        CustomPage<Category> result = categoryUsecase.listCategories(pageNo, pageSize, sortBy, sortDirection);
        assertEquals(expectedPage, result);
        verify(categoryPersistencePort).findAll(pageNo, pageSize, sortBy, sortDirection);
    }

    @Test
    void testListCategoriesWhenDatabaseError() {
        int pageNo = 1;
        int pageSize = 10;
        String sortBy = "name";
        String sortDirection = "asc";


        when(categoryPersistencePort.findAll(pageNo, pageSize, sortBy, sortDirection))
                .thenThrow(new RuntimeException("Database error"));


        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            categoryUsecase.listCategories(pageNo, pageSize, sortBy, sortDirection);
        });

        assertEquals("Database error", thrownException.getMessage());
    }


}