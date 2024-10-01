package com.emazon.emazonstockservice.domain.validator;

import com.emazon.emazonstockservice.domain.constants.ErrorMessagesConstants;
import com.emazon.emazonstockservice.domain.exceptions.DataNotFoundException;
import com.emazon.emazonstockservice.domain.model.Article;
import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.model.stock.StockVerificationRequest;
import com.emazon.emazonstockservice.domain.model.stock.StockVerificationResponse;
import com.emazon.emazonstockservice.domain.ports.spi.ArticlePersistencePort;
import com.emazon.emazonstockservice.domain.ports.spi.StockPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockValidatorTest {

    @Mock
    ArticlePersistencePort articlePersistencePort;

    @Mock
    StockPersistencePort stockPersistencePort;

    @InjectMocks
    StockValidator stockValidator;

    @Test
    void shouldHaveSufficientStockAndNotExceedCategoryLimit() {

        StockVerificationRequest request = new StockVerificationRequest(1L, 5, List.of(2L, 3L));

        Article article = new Article();
        Category category = new Category();
        category.setId(1L);
        article.setCategories(Set.of(category));

        when(articlePersistencePort.findArticleById(1L)).thenReturn(Optional.of(article));
        when(articlePersistencePort.findArticleById(2L)).thenReturn(Optional.of(article));
        when(articlePersistencePort.findArticleById(3L)).thenReturn(Optional.of(article));

        when(stockPersistencePort.findAvailableStockByArticleId(1L)).thenReturn(Optional.of(10));
        StockVerificationResponse response = stockValidator.checkStockAvailability(request);

        assertTrue(response.getSufficientStockAvailable());
        assertFalse(response.getCategoryLimitExceeded());

        verify(articlePersistencePort, times(3)).findArticleById(anyLong());
    }

    @Test
    void shouldHaveInsufficientStock() {

        StockVerificationRequest request = new StockVerificationRequest(1L, 10, List.of());

        Article article = new Article();
        Category category = new Category();
        category.setId(1L);
        article.setCategories(Set.of(category));

        when(stockPersistencePort.findAvailableStockByArticleId(1L)).thenReturn(Optional.of(5));
        when(articlePersistencePort.findArticleById(1L)).thenReturn(Optional.of(article));

        StockVerificationResponse response = stockValidator.checkStockAvailability(request);

        assertFalse(response.getSufficientStockAvailable());
        assertNotNull(response.getNextRestockDate());

        verify(stockPersistencePort, times(1)).findAvailableStockByArticleId(1L);
    }


    @Test
    void shouldThrowExceptionWhenArticleNotFound() {

        StockVerificationRequest request = new StockVerificationRequest(1L, 5, List.of(1L, 2L));

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> stockValidator.checkStockAvailability(request));
        assertEquals(String.format(ErrorMessagesConstants.ARTICLE_NOT_FOUND, 1), exception.getMessage());

    }


    @Test
    void testCategoryLimitExceededWhenAddingFourthArticleToSameCategory() {


        Long articleId = 1L;
        Long cartArticleId1 = 2L;
        Long cartArticleId2 = 3L;
        Long cartArticleId3 = 4L;


        Category commonCategory = new Category(1L, "Common Category", "description");

        Article articleToAdd = new Article(articleId, "New Article", Set.of(commonCategory));
        Article cartArticle1 = new Article(cartArticleId1, "Cart Article 1", Set.of(commonCategory));
        Article cartArticle2 = new Article(cartArticleId2, "Cart Article 2", Set.of(commonCategory));
        Article cartArticle3 = new Article(cartArticleId3, "Cart Article 3", Set.of(commonCategory));

        StockVerificationRequest request = new StockVerificationRequest(articleId, 5, Arrays.asList(cartArticleId1, cartArticleId2, cartArticleId3));

        when(stockPersistencePort.findAvailableStockByArticleId(articleId)).thenReturn(Optional.of(10));
        when(articlePersistencePort.findArticleById(articleId)).thenReturn(Optional.of(articleToAdd));
        when(articlePersistencePort.findArticleById(cartArticleId1)).thenReturn(Optional.of(cartArticle1));
        when(articlePersistencePort.findArticleById(cartArticleId2)).thenReturn(Optional.of(cartArticle2));
        when(articlePersistencePort.findArticleById(cartArticleId3)).thenReturn(Optional.of(cartArticle3));

        StockVerificationResponse response = stockValidator.checkStockAvailability(request);

        assertTrue(response.getCategoryLimitExceeded());
    }


}