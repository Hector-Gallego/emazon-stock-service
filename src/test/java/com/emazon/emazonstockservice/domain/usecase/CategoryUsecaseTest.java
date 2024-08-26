package com.emazon.emazonstockservice.domain.usecase;

import com.emazon.emazonstockservice.domain.exceptions.CategorySaveException;
import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.spi.ICategoryPersistencePort;
import com.emazon.emazonstockservice.domain.util.DomainsConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryUsecaseTest {

    private ICategoryPersistencePort categoryPersistencePort;
    private CategoryUsecase categoryUsecase;

    @BeforeEach
    void setUp() {
        categoryPersistencePort = Mockito.mock(ICategoryPersistencePort.class);
        categoryUsecase = new CategoryUsecase(categoryPersistencePort);
    }

    @Test
    void saveCategory_ShouldSaveCategory_WhenCategoryNameIsUnique() {
        // Arrange
        Category category = new Category();
        category.setName("Electronics");

        when(categoryPersistencePort.existsByName(category.getName())).thenReturn(false);

        // Act
        categoryUsecase.saveCategory(category);

        // Assert
        verify(categoryPersistencePort, times(1)).saveCategory(category);
    }

    @Test
    void saveCategory_ShouldThrowCategorySaveException_WhenCategoryNameIsNotUnique() {
        // Arrange
        Category category = new Category();
        category.setName("Electronics");

        when(categoryPersistencePort.existsByName(category.getName())).thenReturn(true);

        // Act & Assert
        CategorySaveException exception = assertThrows(CategorySaveException.class, () -> {
            categoryUsecase.saveCategory(category);
        });

        assertEquals(DomainsConstants.getDuplicateNameFieldMessage(category.getName()), exception.getMessage());
    }

    @Test
    void saveCategory_ShouldThrowCategorySaveException_WhenPersistenceFails() {
        // Arrange
        Category category = new Category();
        category.setName("Electronics");

        when(categoryPersistencePort.existsByName(category.getName())).thenReturn(false);
        doThrow(new RuntimeException("Database error")).when(categoryPersistencePort).saveCategory(any(Category.class));

        // Act & Assert
        CategorySaveException exception = assertThrows(CategorySaveException.class, () -> {
            categoryUsecase.saveCategory(category);
        });

        assertEquals(DomainsConstants.FAIL_SAVE_CATEGORY_MESSAGE, exception.getMessage());
    }

}