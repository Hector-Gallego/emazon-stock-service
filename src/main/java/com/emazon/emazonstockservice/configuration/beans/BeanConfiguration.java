package com.emazon.emazonstockservice.configuration.beans;


import com.emazon.emazonstockservice.domain.api.ArticleServicePort;
import com.emazon.emazonstockservice.domain.api.BrandServicePort;
import com.emazon.emazonstockservice.domain.api.CategoryServicePort;
import com.emazon.emazonstockservice.domain.spi.ArticlePersistencePort;
import com.emazon.emazonstockservice.domain.spi.BrandPersistencePort;
import com.emazon.emazonstockservice.domain.spi.CategoryPersistencePort;
import com.emazon.emazonstockservice.domain.usecase.ArticleUseCase;
import com.emazon.emazonstockservice.domain.usecase.BrandUseCase;
import com.emazon.emazonstockservice.domain.usecase.CategoryUseCase;
import com.emazon.emazonstockservice.ports.driven.adapter.ArticleJpaAdapter;
import com.emazon.emazonstockservice.ports.driven.adapter.BrandJpaAdapter;
import com.emazon.emazonstockservice.ports.driven.adapter.CategoryJpaAdapter;
import com.emazon.emazonstockservice.ports.driven.mapper.BrandEntityMapper;
import com.emazon.emazonstockservice.ports.driven.mapper.CategoryEntityMapper;
import com.emazon.emazonstockservice.ports.driven.mapper.ArticleEntityMapper;
import com.emazon.emazonstockservice.ports.driven.repository.IArticleRepository;
import com.emazon.emazonstockservice.ports.driven.repository.IBrandRepository;
import com.emazon.emazonstockservice.ports.driven.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {


    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;

    private final IBrandRepository  brandRepository;
    private final BrandEntityMapper brandEntityMapper;

    private final IArticleRepository articleRepository;
    private final ArticleEntityMapper articleEntityMapper;



    @Bean
    public ArticlePersistencePort articlePersistencePort(){
        return new ArticleJpaAdapter( articleRepository, articleEntityMapper, brandEntityMapper, categoryEntityMapper);
    }


    @Bean
    public ArticleServicePort articleServicePort(ArticlePersistencePort articlePersistencePort, CategoryPersistencePort categoryPersistencePort, BrandPersistencePort brandPersistencePort){
        return new ArticleUseCase(articlePersistencePort, categoryPersistencePort, brandPersistencePort);
    }

    @Bean
    public CategoryPersistencePort categoryPersistencePort(){

        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public CategoryServicePort categoryServicePort(CategoryPersistencePort categoryPersistencePort)
    {
        return new CategoryUseCase(categoryPersistencePort);
    }

    @Bean
    BrandPersistencePort brandPersistencePort(){
        return new BrandJpaAdapter(brandRepository, brandEntityMapper);
    }

    @Bean
    BrandServicePort brandServicePort(BrandPersistencePort brandPersistencePort){
        return new BrandUseCase(brandPersistencePort);
    }

}
