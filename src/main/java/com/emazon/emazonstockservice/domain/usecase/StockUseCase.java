package com.emazon.emazonstockservice.domain.usecase;

import com.emazon.emazonstockservice.domain.constants.ErrorMessagesConstants;
import com.emazon.emazonstockservice.domain.exceptions.DataNotFoundException;
import com.emazon.emazonstockservice.domain.exceptions.InsufficientStockException;
import com.emazon.emazonstockservice.domain.model.*;
import com.emazon.emazonstockservice.domain.model.stock.*;
import com.emazon.emazonstockservice.domain.ports.api.StockServicePort;
import com.emazon.emazonstockservice.domain.ports.spi.ArticlePersistencePort;
import com.emazon.emazonstockservice.domain.ports.spi.StockPersistencePort;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.domain.validator.StockValidator;
import com.emazon.emazonstockservice.ports.driven.mysql.constants.ValueConstants;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public class StockUseCase implements StockServicePort {

    private final StockPersistencePort stockPersistencePort;
    private final ArticlePersistencePort articlePersistencePort;
    private final StockValidator stockValidator;


    public StockUseCase(StockPersistencePort stockPersistencePort, ArticlePersistencePort articlePersistencePort, StockValidator stockValidator) {
        this.stockPersistencePort = stockPersistencePort;
        this.articlePersistencePort = articlePersistencePort;
        this.stockValidator = stockValidator;
    }

    @Override
    public void addStock(Long articleId, Integer quantity) {
        Optional<Article> articleOptional = articlePersistencePort.findArticleById(articleId);

        if (articleOptional.isEmpty()) {
            throw new DataNotFoundException(String.format(
                    ErrorMessagesConstants.ARTICLE_NOT_FOUND,
                    articleId));
        }
        stockPersistencePort.addStock(articleId, quantity);
    }

    @Override
    public StockVerificationResponse checkStockAvailability(StockVerificationRequest stockVerificationRequest) {

        return stockValidator.checkStockAvailability(stockVerificationRequest);
    }

    @Override
    public PageArticlesCartResponse<ArticleCart> listArticlesCart(PageArticlesCartRequest pageArticlesCartRequest) {

        List<CartItem> articlesCart = pageArticlesCartRequest.getArticlesCart();
        CustomPage<ArticleCart> customPageCart = stockPersistencePort.getPageArticlesCart(pageArticlesCartRequest, articlesCart);
        BigDecimal totalPurchase = calculateTotalPurchase(articlesCart);

        CustomPage<ArticleCart> verifiedStockPage = evaluateStockAndSetStatus(customPageCart, articlesCart);

        verifiedStockPage.getContent().forEach(articleCart -> {
            articlesCart.stream()
                    .filter(cartItem -> cartItem.getArticleId().equals(articleCart.getId()))
                    .findFirst()
                    .ifPresent(cartItem -> articleCart.setQuantity(cartItem.getQuantity()));
        });


        return new PageArticlesCartResponse<>(verifiedStockPage, totalPurchase);
    }


    private BigDecimal calculateTotalPurchase(List<CartItem> articlesCart) {

        List<Long> ids = articlesCart.stream()
                .map(CartItem::getArticleId)
                .toList();

        List<Article> allArticles = stockPersistencePort.getTotalArticlesCart(ids);

        BigDecimal totalPurchase = BigDecimal.ZERO;

        for (CartItem cartItem : articlesCart) {

            Article article = allArticles.stream()
                    .filter(a -> a.getId().equals(cartItem.getArticleId()))
                    .findFirst().orElse(null);

            if (article != null && article.getQuantity() >= cartItem.getQuantity()) {
                totalPurchase = totalPurchase
                        .add(BigDecimal.valueOf(article.getPrice())
                                .multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            }
        }
        return totalPurchase;
    }

    private CustomPage<ArticleCart> evaluateStockAndSetStatus(CustomPage<ArticleCart> customPage, List<CartItem> articlesCart) {

        customPage.getContent()
                .forEach(articleCart -> {
                    CartItem cartItem = articlesCart.stream()
                            .filter(a -> a.getArticleId().equals(articleCart.getId()))
                            .findFirst().orElse(null);

                    if (cartItem != null) {

                        int quantity = cartItem.getQuantity();

                        if (articleCart.getQuantity() >= quantity) {
                            articleCart.setSufficientStock(true);
                        } else {
                            articleCart.setSufficientStock(false);
                            articleCart.setSupplyDate(LocalDate.now()
                                    .plusDays(ValueConstants.MIN_DAYS_FOR_RESTOCKING_SUPPLY));
                        }
                    }
                });
        return customPage;
    }

    @Override
    public SaleData updateStockAndGetSaleData(List<CartItem> cartItemList) {

        for (CartItem cartItem : cartItemList) {
            Long articleId = cartItem.getArticleId();
            Integer requestedQuantity = cartItem.getQuantity();

            Optional<Integer> availableStockOpt = stockPersistencePort.findAvailableStockByArticleId(articleId);
            if (availableStockOpt.isEmpty() || availableStockOpt.get() < requestedQuantity) {
                throw new InsufficientStockException(String.format(
                        ErrorMessagesConstants.INSUFFICIENT_STOCK_ERROR_MESSAGE,
                        articleId, availableStockOpt.orElse(0), requestedQuantity));
            }
        }
        stockPersistencePort.updateStock(cartItemList);
        List<Long> idsCart = getIdsCart(cartItemList);
        List<Article> articleList = stockPersistencePort.getTotalArticlesCart(idsCart);
        List<ItemSaleData> itemSaleDataList = articleList
                .stream()
                .map(article -> {

                    Integer requestedQuantity = cartItemList.stream()
                            .filter(cartItem -> cartItem.getArticleId().equals(article.getId()))
                            .findFirst()
                            .map(CartItem::getQuantity)
                            .orElse(0);

                    return new ItemSaleData(
                            article.getId(),
                            article.getName(),
                            requestedQuantity,
                            article.getDescription(),
                            article.getPrice()
                    );
                })
                .toList();

        SaleData saleData = new SaleData();
        saleData.setTotalPurchase(calculateTotalPurchase(cartItemList));
        saleData.setItemsSaleData(itemSaleDataList);

        return saleData;
    }

    @Override
    public void updateStockCompensation(List<CartItem> cartItemList) {
        stockPersistencePort.compensateStock(cartItemList);
    }

    private List<Long> getIdsCart(List<CartItem> cartItemList){
        return  cartItemList.stream().map(CartItem::getArticleId).toList();
    }


}
