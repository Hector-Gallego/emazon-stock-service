package com.emazon.emazonstockservice.ports.driven.adapter;

import com.emazon.emazonstockservice.domain.exceptions.DataNotFoundException;
import com.emazon.emazonstockservice.domain.model.Article;
import com.emazon.emazonstockservice.domain.spi.IArticlePersistencePort;
import com.emazon.emazonstockservice.domain.util.BrandConstants;
import com.emazon.emazonstockservice.domain.util.CategoryConstants;
import com.emazon.emazonstockservice.ports.driven.entity.ArticleCategoryEntity;
import com.emazon.emazonstockservice.ports.driven.entity.ArticleEntity;
import com.emazon.emazonstockservice.ports.driven.entity.BrandEntity;
import com.emazon.emazonstockservice.ports.driven.entity.CategoryEntity;
import com.emazon.emazonstockservice.ports.driven.mapper.ArticleEntityMapper;
import com.emazon.emazonstockservice.ports.driven.repository.IArticleRepository;
import com.emazon.emazonstockservice.ports.driven.repository.IBrandRepository;
import com.emazon.emazonstockservice.ports.driven.repository.ICategoryRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public void saveArticle(Article article, List<Long> categoryIds, Long brandId) {


        //mapper article to article entity

        ArticleEntity articleEntity = articleEntityMapper.toEntity(article);


        // Busco la entidad Brand por ID
        BrandEntity brandEntity = brandRepository.findById(brandId)
                .orElseThrow(() -> new DataNotFoundException(String.format(
                        BrandConstants.BRAND_NOT_FOUND, brandId)
                ));

        articleEntity.setBrand(brandEntity);


        // Creo el conjunto de ArticleCategory
        Set<ArticleCategoryEntity> articleCategoryEntities = new HashSet<>();

        for (Long categoryId : categoryIds) {
            // busco la entidad Category por Id
            CategoryEntity categoryEntity = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new DataNotFoundException(String.format(
                            CategoryConstants.CATEGORY_NOT_FOUND, categoryId)));

            // Crear un objeto ArticleCategory
            ArticleCategoryEntity articleCategoryEntity = new ArticleCategoryEntity();

            // Agrego los objetos Article y Category
            articleCategoryEntity.setArticleEntity(articleEntity);
            articleCategoryEntity.setCategoryEntity(categoryEntity);

            // Agregar ArticleCategoryEntity al conjunto
            articleCategoryEntities.add(articleCategoryEntity);
        }

        // Asigno las categorías al artículo
        articleEntity.setArticleCategoryEntities(articleCategoryEntities);

        // guardo el artículo
        articleRepository.save(articleEntity);

    }

    @Override
    public boolean existByName(String name) {
        return articleRepository.findByName(name).isPresent();
    }
}
