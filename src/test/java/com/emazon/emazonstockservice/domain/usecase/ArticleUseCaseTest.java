package com.emazon.emazonstockservice.domain.usecase;


import com.emazon.emazonstockservice.datatest.ArticleDataTestFactory;
import com.emazon.emazonstockservice.datatest.CustomPageDataFactory;
import com.emazon.emazonstockservice.domain.constants.ModelNamesConstants;
import com.emazon.emazonstockservice.domain.exceptions.*;
import com.emazon.emazonstockservice.domain.model.*;
import com.emazon.emazonstockservice.domain.ports.spi.ArticlePersistencePort;
import com.emazon.emazonstockservice.domain.ports.spi.BrandPersistencePort;
import com.emazon.emazonstockservice.domain.ports.spi.CategoryPersistencePort;
import com.emazon.emazonstockservice.domain.constants.ArticleConstants;
import com.emazon.emazonstockservice.domain.constants.BrandConstants;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.domain.constants.ErrorMessagesConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleUseCaseTest {

    @Mock
    ArticlePersistencePort articlePersistencePort;

    @Mock
    BrandPersistencePort brandPersistencePort;

    @Mock
    CategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    ArticleUseCase articleUseCase;


    @Test
    void ShouldSaveArticleWhenItDoesNotExist() {


        List<Long> categoryIds = Arrays.asList(1L, 2L, 3L);

        Set<Category> categories = new HashSet<>();

        Map<Long, Optional<Category>> categoryMap = categoryIds.stream()
                .collect(Collectors.toMap(
                        id -> id,
                        id -> {
                            Category category = new Category();
                            categories.add(category);
                            return Optional.of(category);
                        }
                ));

        Long brandId = 1L;
        Brand brand = new Brand();
        brand.setId(brandId);
        brand.setName("Samsung");

        Article article = ArticleDataTestFactory.createValidArticle();

        when(articlePersistencePort.existByName("Samsung Galaxy S21")).thenReturn(false);
        when(brandPersistencePort.findBrandById(brandId)).thenReturn(Optional.of(brand));
        when(categoryPersistencePort.findCategoryByIds(categoryIds)).thenReturn(categoryMap);


        articleUseCase.saveArticle(article, categoryIds, brandId);

        verify(articlePersistencePort, times(1)).saveArticle(article,categories, brand);

    }

    @Test
    void ShouldThrowExceptionWhenCategoryNameAlreadyExists() {

        List<Long> categoryIds = Arrays.asList(1L, 2L, 3L);
        Long brandId = 1L;

        Article article = ArticleDataTestFactory.createValidArticle();
        when(articlePersistencePort.existByName("Samsung Galaxy S21")).thenReturn(true);

        DuplicateNameException exception = assertThrows(DuplicateNameException.class, () -> articleUseCase.saveArticle(article, categoryIds, brandId));
        assertEquals(ErrorMessagesConstants.
                        getDuplicateNameFieldMessage(
                                ModelNamesConstants.ARTICLE.toString(),
                                article.getName()),
                exception.getMessage());

    }

    @Test
    void shouldThrowExceptionWhenCategoryIdsAreOutOfRange() {

        List<Long> tooManyCategoryIds = Arrays.asList(1L, 2L, 3L, 4L);
        List<Long> noCategoryIds = new ArrayList<>();
        Long brandId = 1L;

        Article article = ArticleDataTestFactory.createValidArticle();

        InvalidCategoryCountException exceptionTooManyCategories = assertThrows(InvalidCategoryCountException.class,
                () -> articleUseCase.saveArticle(article, tooManyCategoryIds, brandId));

        InvalidCategoryCountException exceptionNoCategories = assertThrows(InvalidCategoryCountException.class,
                () -> articleUseCase.saveArticle(article, noCategoryIds, brandId));

        assertEquals(ArticleConstants.CATEGORY_COUNT_OUT_OF_RANGE,
                exceptionTooManyCategories.getMessage());

        assertEquals(ArticleConstants.CATEGORY_COUNT_OUT_OF_RANGE,
                exceptionNoCategories.getMessage());

    }


    @Test
    void ShouldThrowExceptionWhenNameIsEmpty() {

        Long brandId = 1L;
        List<Long> categoryIds = Arrays.asList(1L, 2L, 3L);

        Article article = ArticleDataTestFactory.createArticleWithInvalidName();

        FieldEmptyException exception = assertThrows(FieldEmptyException.class,
                () -> articleUseCase.saveArticle(article, categoryIds, brandId));

        assertEquals(ErrorMessagesConstants.NAME_CANNOT_BE_EMPTY_ERROR_MESSAGE, exception.getMessage());

    }

    @Test
    void ShouldThrowExceptionWhenDescriptionIsEmpty() {


        Long brandId = 1L;
        List<Long> categoryIds = Arrays.asList(1L, 2L, 3L);

        Article article = ArticleDataTestFactory.createArticleWithInvalidDescription();

        FieldEmptyException exception = assertThrows(FieldEmptyException.class,
                () -> articleUseCase.saveArticle(article, categoryIds, brandId));

        assertEquals(ErrorMessagesConstants.DESCRIPTION_CANNOT_BE_EMPTY_ERROR_MESSAGE, exception.getMessage());

    }


    @Test
    void shouldThrowExceptionWhenQuantityIsNegative() {


        Long brandId = 1L;

        List<Long> categoryIds = Arrays.asList(1L, 2L, 3L);
        Article article = ArticleDataTestFactory.createArticleWithQuantityIsNegative();

        InvalidValuesException exception = assertThrows(InvalidValuesException.class,
                () -> articleUseCase.saveArticle(article, categoryIds, brandId));

        assertEquals(String.format(
                ArticleConstants.VALUE_CANNOT_BE_NEGATIVE,
                ArticleConstants.ARTICLE_FIELDS.QUANTITY), exception.getMessage());


    }

    @Test
    void shouldThrowExceptionWhenPriceIsNegative() {


        Long brandId = 1L;
        List<Long> categoryIds = Arrays.asList(1L, 2L, 3L);
        Article article = ArticleDataTestFactory.createArticleWithPriceIsNegative();


        InvalidValuesException exception = assertThrows(InvalidValuesException.class,
                () -> articleUseCase.saveArticle(article, categoryIds, brandId));

        assertEquals(String.format(
                ArticleConstants.VALUE_CANNOT_BE_NEGATIVE,
                ArticleConstants.ARTICLE_FIELDS.PRICE), exception.getMessage());

    }

    @Test
    void shouldThrowExceptionWhenCategoryIsDuplicated() {


        Long brandId = 1L;

        List<Long> categoryIds = Arrays.asList(2L, 2L, 3L);
        Article article = ArticleDataTestFactory.createValidArticle();

        DuplicateCategoryException exception = assertThrows(DuplicateCategoryException.class,
                () -> articleUseCase.saveArticle(article, categoryIds, brandId));

        assertEquals(ArticleConstants.ARTICLE_CANNOT_HAVE_DUPLICATE_CATEGORIES, exception.getMessage());
    }


    @Test
    void shouldThrowExceptionWhenBrandNotFound() {

        Long brandId = 1L;
        List<Long> categoryIds = Arrays.asList(1L, 2L, 3L);

        Article article = ArticleDataTestFactory.createValidArticle();

        when(brandPersistencePort.findBrandById(brandId)).thenReturn(Optional.empty());


        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> articleUseCase.saveArticle(article, categoryIds, brandId));

        assertEquals(String.format(BrandConstants.BRAND_NOT_FOUND, brandId), exception.getMessage());
    }

    @Test
    void ShouldReturnArticlesWhenParametersAreValid() {


        Integer pageNumber = 1;
        Integer pageSize = 10;
        String sortBy = "name";
        String sortDirection = "asc";

        CustomPage<Article> customPage = CustomPageDataFactory.createCustomPageWithParametersAreValid();


        when(articlePersistencePort.findAll(pageNumber, pageSize, sortBy, sortDirection)).thenReturn(customPage);

        CustomPage<Article> result = articleUseCase.listArticles(pageNumber, pageSize, sortBy, sortDirection);

        assertEquals(customPage, result);


    }

    @Test
    void shouldThrowExceptionWhenParametersAreInvalid() {

        Integer pageNumber = -1;
        Integer pageSize = -1;
        String sortBy = "";
        String sortDirection = "ascendente";

        InvalidParameterPaginationException exception = assertThrows(InvalidParameterPaginationException.class, () -> articleUseCase.listArticles(pageNumber, pageSize, sortBy, sortDirection));

        assertEquals(ErrorMessagesConstants.INVALID_PARAMETERS_ERROR_MESSAGE, exception.getMessage());

        List<String> expectedErrors = List.of(
                ErrorMessagesConstants.INVALID_PAGE_NO_ERROR_MESSAGE,
                ErrorMessagesConstants.INVALID_PAGE_SIZE_ERROR_MESSAGE,
                ErrorMessagesConstants.INVALID_SORT_DIRECTION_ERROR_MESSAGE,
                ErrorMessagesConstants.INVALID_SORT_BY_ERROR_MESSAGE

        );

        assertEquals(expectedErrors, exception.getErrors());
    }

}