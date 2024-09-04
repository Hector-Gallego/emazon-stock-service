package com.emazon.emazonstockservice.domain.usecase;


import com.emazon.emazonstockservice.domain.exceptions.*;
import com.emazon.emazonstockservice.domain.model.Article;
import com.emazon.emazonstockservice.domain.model.Brand;
import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.spi.IArticlePersistencePort;
import com.emazon.emazonstockservice.domain.util.ArticleConstants;
import com.emazon.emazonstockservice.domain.util.BrandConstants;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.domain.util.DomainsConstants;
import com.emazon.emazonstockservice.ports.driven.entity.BrandEntity;
import com.emazon.emazonstockservice.ports.driven.entity.CategoryEntity;
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
    IArticlePersistencePort articlePersistencePort;

    @InjectMocks
    ArticleUseCase articleUseCase;


    @Test
    void ShouldSaveArticleWhenItDoesNotExist() {

        // given
        List<Long> categoryIds = Arrays.asList(1L, 2L, 3L);

        Set<CategoryEntity> categoryEntities = new HashSet<>();
        Map<Long, Optional<CategoryEntity>> categoryMap = categoryIds.stream()
                .collect(Collectors.toMap(
                        id -> id,
                        id -> {
                            CategoryEntity categoryEntity = new CategoryEntity();
                            categoryEntities.add(categoryEntity);
                            return Optional.of(categoryEntity);
                        }
                ));

        Long brandId = 1L;
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setId(brandId);
        brandEntity.setName("Samsung");

        Article article = new Article(
                1L,
                "Samsung Galaxy S21",
                "Smartphone con pantalla AMOLED y cámaras avanzadas.",
                10,
                200.0,
                new HashSet<>(),
                new Brand()
        );

        when(articlePersistencePort.existByName("Samsung Galaxy S21")).thenReturn(false);
        when(articlePersistencePort.findBrandById(brandId)).thenReturn(Optional.of(brandEntity));
        when(articlePersistencePort.findCategoryByIds(categoryIds)).thenReturn(categoryMap);

        // act
        articleUseCase.saveArticle(article, categoryIds, brandId);

        // then
        verify(articlePersistencePort, times(1)).saveArticle(article, categoryEntities, brandEntity);

    }

    @Test
    void ShouldThrowExceptionWhenCategoryNameAlreadyExists() {

        //given

        Set<Category> categories = new HashSet<>();
        Brand brand = new Brand();
        List<Long> categoryIds = Arrays.asList(1L, 2L, 3L);


        Long brandId = 1L;

        Article article = new Article(
                1L,
                "Samsung Galaxy S21",
                "Smartphone con pantalla AMOLED y cámaras avanzadas.",
                10,
                200.0,
                categories,
                brand
        );

        //when
        when(articlePersistencePort.existByName("Samsung Galaxy S21")).thenReturn(true);

        //then
        DuplicateNameException exception = assertThrows(DuplicateNameException.class, () -> articleUseCase.saveArticle(article, categoryIds, brandId));
        assertEquals(DomainsConstants.
                        getDuplicateNameFieldMessage(
                                DomainsConstants.MODEL_NAMES.ARTICLE.toString(),
                                article.getName()),
                exception.getMessage());

    }

    @Test
    void shouldThrowExceptionWhenCategoryIdsAreOutOfRange() {
        // Given
        Set<Category> categories = new HashSet<>();
        List<Long> tooManyCategoryIds = Arrays.asList(1L, 2L, 3L, 4L);
        List<Long> noCategoryIds = new ArrayList<>();
        Brand brand = new Brand();
        Long brandId = 1L;

        Article article = new Article(
                1L,
                "Samsung Galaxy S21",
                "Smartphone con pantalla AMOLED y cámaras avanzadas.",
                10,
                200.0,
                categories,
                brand
        );

        // When
        InvalidCategoryCountException exceptionTooManyCategories = assertThrows(InvalidCategoryCountException.class,
                () -> articleUseCase.saveArticle(article, tooManyCategoryIds, brandId));

        InvalidCategoryCountException exceptionNoCategories = assertThrows(InvalidCategoryCountException.class,
                () -> articleUseCase.saveArticle(article, noCategoryIds, brandId));

        //then
        assertEquals(ArticleConstants.CATEGORY_COUNT_OUT_OF_RANGE,
                exceptionTooManyCategories.getMessage());

        assertEquals(ArticleConstants.CATEGORY_COUNT_OUT_OF_RANGE,
                exceptionNoCategories.getMessage());

    }


    @Test
    void ShouldThrowExceptionWhenNameIsEmpty() {

        //given
        Set<Category> categories = new HashSet<>();
        Brand brand = new Brand();
        Long brandId = 1L;

        List<Long> categoryIds = Arrays.asList(1L, 2L, 3L);
        Article article = new Article(
                1L,
                "",
                "Televisor con tecnología Quantum Dot para una calidad de imagen superior.",
                10,
                200.0,
                categories,
                brand
        );

        //then
        FieldEmptyException exception = assertThrows(FieldEmptyException.class,
                () -> articleUseCase.saveArticle(article, categoryIds, brandId));

        assertEquals(DomainsConstants.NAME_CANNOT_BE_EMPTY, exception.getMessage());

    }

    @Test
    void ShouldThrowExceptionWhenDescriptionIsEmpty() {

        //given
        Set<Category> categories = new HashSet<>();
        Brand brand = new Brand();
        Long brandId = 1L;


        List<Long> categoryIds = Arrays.asList(1L, 2L, 3L);
        Article article = new Article(
                1L,
                "Samsung LED TV",
                "",
                10,
                200.0,
                categories,
                brand
        );

        //then
        FieldEmptyException exception = assertThrows(FieldEmptyException.class,
                () -> articleUseCase.saveArticle(article, categoryIds, brandId));

        assertEquals(DomainsConstants.DESCRIPTION_CANNOT_BE_EMPTY, exception.getMessage());

    }


    @Test
    void shouldThrowExceptionWhenQuantityIsNegative() {

        //given
        Set<Category> categories = new HashSet<>();
        Brand brand = new Brand();
        Long brandId = 1L;

        List<Long> categoryIds = Arrays.asList(1L, 2L, 3L);
        Article article = new Article(
                1L,
                "Samsung Galaxy Note 20",
                "Smartphone con lápiz óptico y alto rendimiento.",
                -10,
                200.0,
                categories,
                brand
        );
        //then
        InvalidValuesException exception = assertThrows(InvalidValuesException.class,
                () -> articleUseCase.saveArticle(article, categoryIds, brandId));

        assertEquals(String.format(
                ArticleConstants.VALUE_CANNOT_BE_NEGATIVE,
                ArticleConstants.ARTICLE_FIELDS.QUANTITY), exception.getMessage());


    }

    @Test
    void shouldThrowExceptionWhenPriceIsNegative() {

        //given
        Set<Category> categories = new HashSet<>();
        Brand brand = new Brand();
        Long brandId = 1L;

        // Caso con más de 3 categorías
        List<Long> categoryIds = Arrays.asList(1L, 2L, 3L);
        Article article = new Article(
                1L,
                "Samsung Galaxy Note 20",
                "Smartphone con lápiz óptico y alto rendimiento.",
                10,
                -200.0,
                categories,
                brand
        );

        //then
        InvalidValuesException exception = assertThrows(InvalidValuesException.class,
                () -> articleUseCase.saveArticle(article, categoryIds, brandId));

        assertEquals(String.format(
                ArticleConstants.VALUE_CANNOT_BE_NEGATIVE,
                ArticleConstants.ARTICLE_FIELDS.PRICE), exception.getMessage());

    }

    @Test
    void shouldThrowExceptionWhenCategoryIsDuplicated() {

        //given
        Set<Category> categories = new HashSet<>();
        Brand brand = new Brand();
        Long brandId = 1L;

        List<Long> categoryIds = Arrays.asList(2L, 2L, 3L);
        Article article = new Article(
                1L,
                "Samsung Galaxy Note 20",
                "Smartphone con lápiz óptico y alto rendimiento.",
                10,
                200.0,
                categories,
                brand
        );
        //then
        DuplicateCategoryException exception = assertThrows(DuplicateCategoryException.class,
                () -> articleUseCase.saveArticle(article, categoryIds, brandId));

        assertEquals(ArticleConstants.ARTICLE_CANNOT_HAVE_DUPLICATE_CATEGORIES, exception.getMessage());
    }


    @Test
    void shouldThrowExceptionWhenBrandNotFound() {
        // Given
        Article article = new Article(
                1L,
                "Samsung Galaxy S21",
                "Smartphone con pantalla AMOLED y cámaras avanzadas.",
                10,
                200.0,
                new HashSet<>(),
                null
        );
        List<Long> categoryIds = Arrays.asList(1L, 2L, 3L);
        Long brandId = 1L;


        when(articlePersistencePort.findBrandById(brandId)).thenReturn(Optional.empty());

        // When & Then
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> articleUseCase.saveArticle(article, categoryIds, brandId));

        assertEquals(String.format(BrandConstants.BRAND_NOT_FOUND, brandId), exception.getMessage());
    }





    @Test
    void ShouldReturnArticlesWhenParametersAreValid() {

        // given
        Integer pageNumber = 1;
        Integer pageSize = 10;
        String sortBy = "name";
        String sortDirection = "asc";

        CustomPage<Article> customPage = new CustomPage.Builder<Article>()
                .content(new ArrayList<>())
                .pageNumber(1)
                .pageSize(10)
                .totalElements(5L)
                .totalPages(5)
                .first(true)
                .last(false)
                .build();

        // when
        when(articlePersistencePort.findAll(pageNumber, pageSize, sortBy, sortDirection)).thenReturn(customPage);
        //act
        CustomPage<Article> result = articleUseCase.listArticles(pageNumber, pageSize, sortBy, sortDirection);
        // then
        assertEquals(customPage, result);


    }

    @Test
    void shouldThrowExceptionWhenParametersAreInvalid() {
        // given
        Integer pageNumber = -1;
        Integer pageSize = -1;
        String sortBy = "";
        String sortDirection = "ascendente";

        // when & then
        InvalidParameterPaginationException exception = assertThrows(InvalidParameterPaginationException.class, () -> articleUseCase.listArticles(pageNumber, pageSize, sortBy, sortDirection));

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