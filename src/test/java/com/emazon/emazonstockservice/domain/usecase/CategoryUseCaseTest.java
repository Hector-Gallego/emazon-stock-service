package com.emazon.emazonstockservice.domain.usecase;

import com.emazon.emazonstockservice.domain.exceptions.DuplicateNameException;
import com.emazon.emazonstockservice.domain.exceptions.FieldEmptyException;
import com.emazon.emazonstockservice.domain.exceptions.FieldLimitExceededException;
import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.spi.ICategoryPersistencePort;
import com.emazon.emazonstockservice.domain.util.CategoryConstants;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CategoryUseCaseTest {


    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    

    @Test
    void ShouldSaveCategoryWhenItDoesNotExist() {

        //given
        Category category = new Category(1L, "category_1", "description_1");

        //when
        when(categoryPersistencePort.existsByName("category_1")).thenReturn(false);

        //act
        categoryUseCase.saveCategory(category);

        //then
        verify(categoryPersistencePort, times(1)).saveCategory(category);

    }

    @Test
    void ShouldThrowExceptionWhenCategoryNameAlreadyExists() {

        //given
        Category category = new Category(1L, "category_1", "description_1");

        //when
        when(categoryPersistencePort.existsByName("category_1")).thenReturn(true);

        //then
        DuplicateNameException exception = assertThrows(DuplicateNameException.class, () -> {
            categoryUseCase.saveCategory(category);
        });


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
                "description_1");

        //then
        FieldLimitExceededException exception = assertThrows(FieldLimitExceededException.class, () -> {
            categoryUseCase.saveCategory(category);
        });

        assertEquals( String.format(
                DomainsConstants.MAX_NAME_LENGTH_MESSAGE,
                CategoryConstants.MAX_CATEGORY_NAME_LENGTH), exception.getMessage());


        verify(categoryPersistencePort, times(0)).saveCategory(category);

    }


    @Test
    void ShouldThrowExceptionWhenNameIsEmpty() {

        //given
        Category category = new Category(
                1L,
                "",
                "description_1");

        //then
        FieldEmptyException exception = assertThrows(FieldEmptyException.class, () -> {
            categoryUseCase.saveCategory(category);
        });

        assertEquals(DomainsConstants.NAME_CANNOT_BE_EMPTY, exception.getMessage());

        verify(categoryPersistencePort, times(0)).saveCategory(category);

    }

    @Test
    void ShouldThrowExceptionWhenDescriptionIsEmpty() {

        //given
        Category category = new Category(
                1L,
                "name_1",
                "");

        //then
        FieldEmptyException exception = assertThrows(FieldEmptyException.class, () -> {
            categoryUseCase.saveCategory(category);
        });

        assertEquals(DomainsConstants.DESCRIPTION_CANNOT_BE_EMPTY, exception.getMessage());

        verify(categoryPersistencePort, times(0)).saveCategory(category);

    }


    @Test
    void ShouldThrowExceptionWhenDescriptionExceedsMaxLength() {

        //given
        Category category = new Category(
                1L,
                "name_1",
                "a".repeat(CategoryConstants.MAX_CATEGORY_DESCRIPTION_LENGTH + 1));

        //then
        FieldLimitExceededException exception = assertThrows(
                FieldLimitExceededException.class, () -> {
                    categoryUseCase.saveCategory(category);
                });

        assertEquals(String.format(
                DomainsConstants.MAX_DESCRIPTION_LENGTH_MESSAGE,
                CategoryConstants.MAX_CATEGORY_DESCRIPTION_LENGTH), exception.getMessage());


        verify(categoryPersistencePort, times(0)).saveCategory(category);

    }






    @Test
    void ShouldReturnCategoryWhenParametersAreValid() {

        // given
        Integer pageNo = 1;
        Integer pageSize = 10;
        String sortBy = "name";
        String sortDirection = "asc";

        CustomPage<Category> customPage = new CustomPage.Builder<Category>()
                .content(new ArrayList<>())
                .pageNumber(1)
                .pageSize(10)
                .totalElements(5L)
                .totalPages(5)
                .first(true)
                .last(false)
                .build();


        // when
        when(categoryPersistencePort.findAll(pageNo, pageSize, sortBy, sortDirection)).thenReturn(customPage);

        //act
        CustomPage<Category> result = categoryUseCase.listCategories(pageNo, pageSize, sortBy, sortDirection);

        // then
        assertEquals(customPage, result);
        verify(categoryPersistencePort, times(1)).findAll(pageNo, pageSize, sortBy, sortDirection);
    }

    @Test
    void ShouldReturnCategoriesWhenParametersAreInvalid() {
        // given
        Integer pageNo = null;
        Integer pageSize = -1;
        String sortBy = "";
        String sortDirection = "ascendente";

        CustomPage<Category> customPage = new CustomPage.Builder<Category>()
                .content(Collections.singletonList(new Category(1L, "category_1", "description_1")))
                .pageNumber(PaginationValidator.DEFAULT_PAGE_NO)
                .pageSize(PaginationValidator.DEFAULT_PAGE_SIZE)
                .totalElements(1L)
                .totalPages(1)
                .first(true)
                .last(true)
                .build();

        when(categoryPersistencePort.findAll(
                PaginationValidator.DEFAULT_PAGE_NO,
                PaginationValidator.DEFAULT_PAGE_SIZE,
                PaginationValidator.DEFAULT_SORT_BY,
                PaginationValidator.DEFAULT_SORT_DIRECTION
        )).thenReturn(customPage);

        // act
        CustomPage<Category> result = categoryUseCase.listCategories(pageNo, pageSize, sortBy, sortDirection);

        // then
        assertEquals(customPage, result);

        verify(categoryPersistencePort, times(1)).findAll(
                PaginationValidator.DEFAULT_PAGE_NO,
                PaginationValidator.DEFAULT_PAGE_SIZE,
                PaginationValidator.DEFAULT_SORT_BY,
                PaginationValidator.DEFAULT_SORT_DIRECTION
        );
    }


    @Test
    void ShouldReturnCategoriesInAscendingOrder() {
        // given
        Integer pageNo = 0;
        Integer pageSize = 10;
        String sortBy = "name";
        String sortDirection = "asc";


        List<Category> categories = Arrays.asList(
                new Category(1L, "A_Category", "description_1"),
                new Category(2L, "B_Category", "description_2"),
                new Category(3L, "C_Category", "description_3")
        );

        CustomPage<Category> customPage = new CustomPage.Builder<Category>()
                .content(categories)
                .pageNumber(pageNo)
                .pageSize(pageSize)
                .totalElements((long) categories.size())
                .totalPages(1)
                .first(true)
                .last(true)
                .build();

        when(categoryPersistencePort.findAll(
                pageNo,
                pageSize,
                sortBy,
                sortDirection
        )).thenReturn(customPage);

        // act
        CustomPage<Category> result = categoryUseCase.listCategories(pageNo, pageSize, sortBy, sortDirection);

        // then
        assertEquals(customPage, result);

        verify(categoryPersistencePort, times(1)).findAll(
                pageNo,
                pageSize,
                sortBy,
                sortDirection
        );


    }



    @Test
    void ShouldReturnCategoriesInDescendingOrder() {
        // given
        Integer pageNo = 0;
        Integer pageSize = 10;
        String sortBy = "name";
        String sortDirection = "desc";


        List<Category> categories = Arrays.asList(
                new Category(1L, "C_Category", "description_1"),
                new Category(2L, "B_Category", "description_2"),
                new Category(3L, "A_Category", "description_3")
        );

        CustomPage<Category> customPage = new CustomPage.Builder<Category>()
                .content(categories)
                .pageNumber(pageNo)
                .pageSize(pageSize)
                .totalElements((long) categories.size())
                .totalPages(1)
                .first(true)
                .last(true)
                .build();

        when(categoryPersistencePort.findAll(
                pageNo,
                pageSize,
                sortBy,
                sortDirection
        )).thenReturn(customPage);

        // act
        CustomPage<Category> result = categoryUseCase.listCategories(pageNo, pageSize, sortBy, sortDirection);

        // then
        assertEquals(customPage, result);

        verify(categoryPersistencePort, times(1)).findAll(
                pageNo,
                pageSize,
                sortBy,
                sortDirection
        );


    }





}