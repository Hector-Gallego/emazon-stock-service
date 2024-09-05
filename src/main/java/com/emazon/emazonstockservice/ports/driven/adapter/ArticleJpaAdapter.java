package com.emazon.emazonstockservice.ports.driven.adapter;

import com.emazon.emazonstockservice.domain.model.Article;
import com.emazon.emazonstockservice.domain.spi.IArticlePersistencePort;
import com.emazon.emazonstockservice.domain.util.CustomPage;
import com.emazon.emazonstockservice.ports.driven.entity.ArticleEntity;
import com.emazon.emazonstockservice.ports.driven.entity.BrandEntity;
import com.emazon.emazonstockservice.ports.driven.entity.CategoryEntity;
import com.emazon.emazonstockservice.ports.driven.mapper.ArticleEntityMapper;
import com.emazon.emazonstockservice.ports.driven.mapper.ArticlePageMapper;
import com.emazon.emazonstockservice.ports.driven.repository.IArticleRepository;
import com.emazon.emazonstockservice.ports.driven.repository.IBrandRepository;
import com.emazon.emazonstockservice.ports.driven.repository.ICategoryRepository;
import com.emazon.emazonstockservice.ports.util.ArticleSortOptions;
import com.emazon.emazonstockservice.ports.util.SortUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


public class ArticleJpaAdapter implements IArticlePersistencePort {

    private final ICategoryRepository categoryRepository;
    private final IArticleRepository articleRepository;
    private final IBrandRepository brandRepository;
    private final ArticleEntityMapper articleEntityMapper;


    public ArticleJpaAdapter(ICategoryRepository categoryRepository,
                             IArticleRepository articleRepository,
                             IBrandRepository brandRepository,
                             ArticleEntityMapper articleEntityMapper) {
        this.categoryRepository = categoryRepository;
        this.articleRepository = articleRepository;
        this.brandRepository = brandRepository;
        this.articleEntityMapper = articleEntityMapper;
    }


    @Override
    public void saveArticle(Article article, Set<CategoryEntity> categoryEntities, BrandEntity brandEntity) {

        ArticleEntity articleEntity = articleEntityMapper.toEntity(article);
        articleEntity.setBrandEntity(brandEntity);
        articleEntity.setCategoryEntities(categoryEntities);
        articleRepository.save(articleEntity);
    }

    @Override
    public boolean existByName(String name) {
        return articleRepository.findByName(name).isPresent();
    }


    @Override
    public Optional<BrandEntity> findBrandById(Long brandId) {
        return brandRepository.findById(brandId);
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
    public Map<Long, Optional<CategoryEntity>> findCategoryByIds(List<Long> categoryIds) {
        return categoryIds.stream()
                .collect(Collectors.toMap(
                        id -> id,
                        categoryRepository::findById
                ));
    }


}