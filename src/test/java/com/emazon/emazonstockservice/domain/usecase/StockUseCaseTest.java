package com.emazon.emazonstockservice.domain.usecase;

import com.emazon.emazonstockservice.domain.constants.ErrorMessagesConstants;
import com.emazon.emazonstockservice.domain.exceptions.DataNotFoundException;
import com.emazon.emazonstockservice.domain.model.Article;
import com.emazon.emazonstockservice.domain.model.stock.StockVerificationRequest;
import com.emazon.emazonstockservice.domain.model.stock.StockVerificationResponse;
import com.emazon.emazonstockservice.domain.ports.spi.ArticlePersistencePort;
import com.emazon.emazonstockservice.domain.ports.spi.StockPersistencePort;
import com.emazon.emazonstockservice.domain.validator.StockValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockUseCaseTest {


    @Mock
    private StockPersistencePort stockPersistencePort;

    @Mock
    ArticlePersistencePort articlePersistencePort;

    @Mock
    StockValidator stockValidator;

    @InjectMocks
    private StockUseCase stockUseCase;

    @Test
    void shouldUpdateStockAndGetSaleDataWhenArticleExists(){

        Long articleId = 1L;
        Integer quantity = 10;

        when(articlePersistencePort.findArticleById(articleId)).thenReturn(Optional.of(new Article()));

        stockUseCase.addStock(articleId, quantity);

        verify(articlePersistencePort).findArticleById(articleId);
        verify(stockPersistencePort).addStock(articleId, quantity);

    }

    @Test
    void shouldThrowDataNotFoundExceptionWhenArticleDoesNotExist(){
        Long articleId = 1L;
        Integer quantity = 10;


        when(articlePersistencePort.findArticleById(articleId)).thenReturn(Optional.empty());

        DataNotFoundException exception = assertThrows(
                DataNotFoundException.class,
                () -> stockUseCase.addStock(articleId, quantity)
        );

        assertEquals(String.format(ErrorMessagesConstants.ARTICLE_NOT_FOUND, articleId), exception.getMessage());
        verify(stockPersistencePort, never()).addStock(articleId, quantity);
    }


    @Test
    void shouldCheckStockAvailability() {

        StockVerificationRequest stockRequest = new StockVerificationRequest();
        stockRequest.setArticleId(1L);
        stockRequest.setQuantity(5);

        StockVerificationResponse expectedResponse = new StockVerificationResponse();
        expectedResponse.setSufficientStockAvailable(true);
        expectedResponse.setCategoryLimitExceeded(false);

        when(stockValidator.checkStockAvailability(stockRequest)).thenReturn(expectedResponse);
        StockVerificationResponse actualResponse = stockUseCase.checkStockAvailability(stockRequest);

        verify(stockValidator, times(1)).checkStockAvailability(stockRequest);
        assertEquals(expectedResponse, actualResponse);
    }


    @Test
    void shouldAddStockWhenArticleExists() {
        Long articleId = 1L;
        Integer quantity = 10;

        when(articlePersistencePort.findArticleById(articleId)).thenReturn(Optional.of(new Article()));
        stockUseCase.addStock(articleId, quantity);
        verify(articlePersistencePort, times(1)).findArticleById(articleId);

        verify(stockPersistencePort, times(1)).addStock(articleId, quantity);
    }

}