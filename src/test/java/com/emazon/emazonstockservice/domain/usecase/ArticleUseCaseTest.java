package com.emazon.emazonstockservice.domain.usecase;


import com.emazon.emazonstockservice.domain.exceptions.*;
import com.emazon.emazonstockservice.domain.model.Article;
import com.emazon.emazonstockservice.domain.model.Brand;
import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.spi.IArticlePersistencePort;
import com.emazon.emazonstockservice.domain.util.ArticleConstans;
import com.emazon.emazonstockservice.domain.util.DomainsConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

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

        //given

        Set<Category> categories = new HashSet<>();
        Brand brand = new Brand();

        List<Long> categoryIds = Arrays.asList(1L, 2L, 3L);

        Long brandId = 1L;

        Article article = new Article(
                1L,
                "article_1",
                "description",
                10,
                200.0,
                categories,
                brand
        );

        //when
        when(articlePersistencePort.existByName("article_1")).thenReturn(false);

        //act
        articleUseCase.saveArticle(article, categoryIds, brandId);

        //then
        verify(articlePersistencePort, times(1)).saveArticle(article, categoryIds, brandId);

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
                "article_1",
                "description",
                10,
                200.0,
                categories,
                brand
        );

        //when
        when(articlePersistencePort.existByName("article_1")).thenReturn(true);

        //then
        DuplicateNameException exception = assertThrows(DuplicateNameException.class, () -> {
            articleUseCase.saveArticle(article, categoryIds, brandId);
        });


        assertEquals(DomainsConstants.
                        getDuplicateNameFieldMessage(
                                DomainsConstants.MODEL_NAMES.ARTICLE.toString(),
                                article.getName()),
                exception.getMessage());

        verify(articlePersistencePort, times(0)).saveArticle(article, categoryIds, brandId);

    }

    @Test
    void shouldThrowExceptionWhenCategoryIdsAreOutOfRange() {
        // Given
        Set<Category> categories = new HashSet<>();
        List<Long> tooManyCategoryIds = Arrays.asList(1L, 2L, 3L, 4L);// Case with more than 3 categories
        List<Long> noCategoryIds = new ArrayList<>();//No categories case
        Brand brand = new Brand();
        Long brandId = 1L;

        Article article = new Article(
                1L,
                "article_1",
                "description",
                10,
                200.0,
                categories,
                brand
        );


        // When
        InvalidCategoryCountException exceptionTooManyCategories = assertThrows(InvalidCategoryCountException.class, () -> {
            articleUseCase.saveArticle(article, tooManyCategoryIds, brandId);
        });


        InvalidCategoryCountException exceptionNoCategories = assertThrows(InvalidCategoryCountException.class, () -> {
            articleUseCase.saveArticle(article, noCategoryIds, brandId);
        });

        //then
        assertEquals(ArticleConstans.CATEGORY_COUNT_OUT_OF_RANGE,
                exceptionTooManyCategories.getMessage());

        assertEquals(ArticleConstans.CATEGORY_COUNT_OUT_OF_RANGE,
                exceptionNoCategories.getMessage());

    }


    @Test
    void ShouldThrowExceptionWhenNameIsEmpty() {

        //given
        Set<Category> categories = new HashSet<>();
        Brand brand = new Brand();
        Long brandId = 1L;

        // Caso con más de 3 categorías
        List<Long> categoryIds = Arrays.asList(1L, 2L, 3L);
        Article article = new Article(
                1L,
                "",
                "description",
                10,
                200.0,
                categories,
                brand
        );

        //then
        FieldEmptyException exception = assertThrows(FieldEmptyException.class, () -> {
            articleUseCase.saveArticle(article, categoryIds, brandId);
        });

        assertEquals(DomainsConstants.NAME_CANNOT_BE_EMPTY, exception.getMessage());

        verify(articlePersistencePort, times(0)).saveArticle(article, categoryIds, brandId);

    }

    @Test
    void ShouldThrowExceptionWhenDescriptionIsEmpty() {

        //given
        Set<Category> categories = new HashSet<>();
        Brand brand = new Brand();
        Long brandId = 1L;

        // Caso con más de 3 categorías
        List<Long> categoryIds = Arrays.asList(1L, 2L, 3L);
        Article article = new Article(
                1L,
                "Article_1",
                "",
                10,
                200.0,
                categories,
                brand
        );

        //then
        FieldEmptyException exception = assertThrows(FieldEmptyException.class, () -> {
            articleUseCase.saveArticle(article, categoryIds, brandId);
        });

        assertEquals(DomainsConstants.DESCRIPTION_CANNOT_BE_EMPTY, exception.getMessage());

        verify(articlePersistencePort, times(0)).saveArticle(article, categoryIds, brandId);

    }


    @Test
    void shouldThrowExceptionWhenQuantityIsNegative() {

        //given
        Set<Category> categories = new HashSet<>();
        Brand brand = new Brand();
        Long brandId = 1L;

        // Caso con más de 3 categorías
        List<Long> categoryIds = Arrays.asList(1L, 2L, 3L);
        Article article = new Article(
                1L,
                "Article_1",
                "description",
                -10,
                200.0,
                categories,
                brand
        );

        //then
        InvalidValuesException exception = assertThrows(InvalidValuesException.class, () -> {
            articleUseCase.saveArticle(article, categoryIds, brandId);
        });

        assertEquals(String.format(
                ArticleConstans.VALUE_CANNOT_BE_NEGATIVE,
                ArticleConstans.ARTICLE_FIELDS.QUANTITY), exception.getMessage());

        verify(articlePersistencePort, times(0)).saveArticle(article, categoryIds, brandId);


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
                "Article_1",
                "description",
                10,
                -200.0,
                categories,
                brand
        );

        //then
        InvalidValuesException exception = assertThrows(InvalidValuesException.class, () -> {
            articleUseCase.saveArticle(article, categoryIds, brandId);
        });

        assertEquals(String.format(
                ArticleConstans.VALUE_CANNOT_BE_NEGATIVE,
                ArticleConstans.ARTICLE_FIELDS.PRICE), exception.getMessage());

        verify(articlePersistencePort, times(0)).saveArticle(article, categoryIds, brandId);


    }

    @Test
    void shouldThrowExceptionWhenCategoryIsDuplicated() {

        //given
        Set<Category> categories = new HashSet<>();
        Brand brand = new Brand();
        Long brandId = 1L;

        // Caso con más de 3 categorías
        List<Long> categoryIds = Arrays.asList(2L, 2L, 3L);
        Article article = new Article(
                1L,
                "Article_1",
                "description",
                10,
                200.0,
                categories,
                brand
        );

        //then
        DuplicateCategoryException exception = assertThrows(DuplicateCategoryException.class, () -> {
            articleUseCase.saveArticle(article, categoryIds, brandId);
        });

        assertEquals(ArticleConstans.ARTICLE_CANNOT_HAVE_DUPLICATE_CATEGORIES, exception.getMessage());

        verify(articlePersistencePort, times(0)).saveArticle(article, categoryIds, brandId);


    }


}