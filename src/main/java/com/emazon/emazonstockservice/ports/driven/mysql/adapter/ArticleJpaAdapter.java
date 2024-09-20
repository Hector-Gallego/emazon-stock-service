package com.emazon.emazonstockservice.ports.driven.mysql.adapter;

import com.emazon.emazonstockservice.domain.constants.ErrorMessagesConstants;
import com.emazon.emazonstockservice.domain.exceptions.DataNotFoundException;
import com.emazon.emazonstockservice.domain.model.Article;
import com.emazon.emazonstockservice.domain.model.Brand;
import com.emazon.emazonstockservice.domain.model.Category;
import com.emazon.emazonstockservice.domain.spi.ArticlePersistencePort;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.ports.driven.mysql.entity.ArticleEntity;
import com.emazon.emazonstockservice.ports.driven.mysql.entity.CategoryEntity;
import com.emazon.emazonstockservice.ports.driven.mysql.mapper.ArticleEntityMapper;
import com.emazon.emazonstockservice.ports.driven.mysql.mapper.paginationmapper.ArticlePageMapper;
import com.emazon.emazonstockservice.ports.driven.mysql.mapper.BrandEntityMapper;
import com.emazon.emazonstockservice.ports.driven.mysql.mapper.CategoryEntityMapper;
import com.emazon.emazonstockservice.ports.driven.mysql.repository.IArticleRepository;
import com.emazon.emazonstockservice.ports.driven.mysql.sortutils.ArticleSortOptions;
import com.emazon.emazonstockservice.ports.driven.mysql.sortutils.SortUtil;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


public class ArticleJpaAdapter implements ArticlePersistencePort {


    private final IArticleRepository articleRepository;
    private final ArticleEntityMapper articleEntityMapper;
    private final BrandEntityMapper brandEntityMapper;
    private final CategoryEntityMapper categoryEntityMapper;


    public ArticleJpaAdapter(
            IArticleRepository articleRepository,
            ArticleEntityMapper articleEntityMapper, BrandEntityMapper brandEntityMapper, CategoryEntityMapper categoryEntityMapper) {

        this.articleRepository = articleRepository;
        this.articleEntityMapper = articleEntityMapper;
        this.brandEntityMapper = brandEntityMapper;
        this.categoryEntityMapper = categoryEntityMapper;
    }


    @Override
    public void saveArticle(Article article, Set<Category> categories, Brand brand) {

        ArticleEntity articleEntity = articleEntityMapper.toEntity(article);
        articleEntity.setBrandEntity(brandEntityMapper.toEntity(brand));

        Set<CategoryEntity> categoryEntities = categories.stream()
                .map(categoryEntityMapper::toEntity)
                .collect(Collectors.toSet());

        articleEntity.setCategoryEntities(categoryEntities);
        articleRepository.save(articleEntity);
    }

    @Override
    public boolean existByName(String name) {
        return articleRepository.findByName(name).isPresent();
    }


    @Override
    public CustomPage<Article> findAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {

        ArticleSortOptions sortOption = ArticleSortOptions.valueOf(sortBy.toUpperCase());

        Sort articleSort = SortUtil.getSort(sortOption, sortDirection);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, articleSort);
        Page<ArticleEntity> page = articleRepository.findAll(pageable);

        return new ArticlePageMapper(articleEntityMapper).toCustomPage(page);


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

    @Transactional
    @Override
    public Optional<Article> findArticleById(Long articleId) {
        return articleRepository.findById(articleId)
                .map(articleEntityMapper::toDomain);
    }


}