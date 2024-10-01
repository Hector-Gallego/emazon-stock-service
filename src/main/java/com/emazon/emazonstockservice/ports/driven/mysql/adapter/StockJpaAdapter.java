package com.emazon.emazonstockservice.ports.driven.mysql.adapter;

import com.emazon.emazonstockservice.domain.constants.ErrorMessagesConstants;
import com.emazon.emazonstockservice.domain.exceptions.DataNotFoundException;
import com.emazon.emazonstockservice.domain.model.Article;
import com.emazon.emazonstockservice.domain.model.stock.ArticleCart;
import com.emazon.emazonstockservice.domain.model.stock.CartItem;
import com.emazon.emazonstockservice.domain.ports.spi.StockPersistencePort;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.ports.driven.mysql.constants.ValueConstants;
import com.emazon.emazonstockservice.ports.driven.mysql.entity.ArticleEntity;
import com.emazon.emazonstockservice.ports.driven.mysql.mapper.ArticleCartEntityMapper;
import com.emazon.emazonstockservice.ports.driven.mysql.mapper.ArticleEntityMapper;
import com.emazon.emazonstockservice.ports.driven.mysql.mapper.paginationmapper.ArticleCartPageMapper;
import com.emazon.emazonstockservice.ports.driven.mysql.repository.IArticleRepository;
import com.emazon.emazonstockservice.domain.model.stock.PageArticlesCartRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class StockJpaAdapter implements StockPersistencePort {

    private final IArticleRepository articleRepository;
    private final ArticleEntityMapper articleEntityMapper;
    private final ArticleCartEntityMapper articleCartEntityMapper;

    public StockJpaAdapter(IArticleRepository articleRepository, ArticleEntityMapper articleEntityMapper, ArticleCartEntityMapper articleCartEntityMapper) {
        this.articleRepository = articleRepository;
        this.articleEntityMapper = articleEntityMapper;
        this.articleCartEntityMapper = articleCartEntityMapper;
    }


    @Override
    public void addStock(Long articleId, Integer quantity) {

        Optional<ArticleEntity> articleOpt = articleRepository.findById(articleId);

        if (articleOpt.isPresent()) {
            ArticleEntity article = articleOpt.get();
            article.setQuantity(article.getQuantity() + quantity);
            articleRepository.save(article);
        } else {
            throw new DataNotFoundException(String.format(
                    ErrorMessagesConstants.ARTICLE_NOT_FOUND, articleId));
        }

    }

    @Override
    public Optional<Integer> findAvailableStockByArticleId(Long articleId) {
        return articleRepository.findAvailableStockByArticleId(articleId);
    }

    @Override
    public CustomPage<ArticleCart> getPageArticlesCart(PageArticlesCartRequest pageArticlesCartRequest, List<CartItem> articlesCart) {

        Integer pageNumber = pageArticlesCartRequest.getPageNumber();
        Integer pageSize = pageArticlesCartRequest.getPageSize();
        String sortOrder = pageArticlesCartRequest.getSortOrder();
        String categoryName = pageArticlesCartRequest.getCategoryNameFilter();
        String brandName = pageArticlesCartRequest.getBrandNameFilter();

        List<Long> ids = articlesCart.stream()
                .map(CartItem::getArticleId)
                .toList();

        Sort sort = Sort.by(ValueConstants.DEFAULT_SORT_OPTION);
        sort.ascending();
        if (ValueConstants.SORT_BY_DESC.equalsIgnoreCase(sortOrder)) {
            sort = sort.descending();
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<ArticleEntity> articlePage = articleRepository.findByIdInAndFilter(ids, brandName, categoryName, pageable);

        return new ArticleCartPageMapper(articleCartEntityMapper).toCustomPage(articlePage);
    }

    @Override
    public List<Article> getTotalArticlesCart(List<Long> ids) {
        return articleRepository.findByIdIn(ids)
                .stream()
                .map(articleEntityMapper::toDomain)
                .toList();
    }

    @Transactional
    @Override
    public void updateStock(List<CartItem> cartItems) {

        for (CartItem cartItem : cartItems) {
            Long articleId = cartItem.getArticleId();
            Integer requestedQuantity = cartItem.getQuantity();
            addStock(articleId, -requestedQuantity);
        }

    }


}
