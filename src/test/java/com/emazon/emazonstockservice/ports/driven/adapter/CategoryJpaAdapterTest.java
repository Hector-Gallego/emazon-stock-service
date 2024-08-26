package com.emazon.emazonstockservice.ports.driven.adapter;

import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.ports.driven.entity.CategoryEntity;
import com.emazon.emazonstockservice.ports.driven.mapper.CategoryEntityMapper;
import com.emazon.emazonstockservice.ports.driven.repository.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryJpaAdapterTest {

    private ICategoryRepository categoryRepository;
    private CategoryEntityMapper categoryEntityMapper;
    private CategoryJpaAdapter categoryJpaAdapter;

    @BeforeEach
    void setUp() {
        categoryRepository = Mockito.mock(ICategoryRepository.class);
        categoryEntityMapper = Mockito.mock(CategoryEntityMapper.class);
        categoryJpaAdapter = new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Test
    void saveCategory_ShouldInvokeSaveOnRepository() {
        // Arrange
        Category category = new Category();
        category.setName("Electronics");
        category.setDescription("Category for electronic items");

        CategoryEntity categoryEntity = new CategoryEntity();
        when(categoryEntityMapper.toEntity(any(Category.class))).thenReturn(categoryEntity);

        // Act
        categoryJpaAdapter.saveCategory(category);

        // Assert
        verify(categoryEntityMapper).toEntity(category);
        verify(categoryRepository).save(categoryEntity);
    }

    @Test
    void existsByName_ShouldReturnTrue_WhenCategoryExists() {
        // Arrange
        String categoryName = "Electronics";

        CategoryEntity categoryEntity = new CategoryEntity();
        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.of(categoryEntity));

        // Act
        boolean result = categoryJpaAdapter.existsByName(categoryName);

        // Assert
        assertTrue(result);
        verify(categoryRepository, times(1)).findByName(categoryName);
    }

    @Test
    void existsByName_ShouldReturnFalse_WhenCategoryDoesNotExist() {
        // Arrange
        String categoryName = "NonExistingCategory";

        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.empty());

        // Act
        boolean result = categoryJpaAdapter.existsByName(categoryName);

        // Assert
        assertFalse(result);
        verify(categoryRepository, times(1)).findByName(categoryName);
    }

}