package com.emazon.emazonstockservice.configuration.beans;


import com.emazon.emazonstockservice.domain.api.IArticleServicePort;
import com.emazon.emazonstockservice.domain.api.IBrandServicePort;
import com.emazon.emazonstockservice.domain.api.ICategoryServicePort;
import com.emazon.emazonstockservice.domain.spi.IArticlePersistencePort;
import com.emazon.emazonstockservice.domain.spi.IBrandPersistencePort;
import com.emazon.emazonstockservice.domain.spi.ICategoryPersistencePort;
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
    public IArticlePersistencePort articlePersistencePort(){
        return new ArticleJpaAdapter(categoryRepository, articleRepository, brandRepository, articleEntityMapper);
    }


    @Bean
    public IArticleServicePort articleServicePort(IArticlePersistencePort articlePersistencePort){
        return new ArticleUseCase(articlePersistencePort);
    }

    @Bean
    public ICategoryPersistencePort categoryPersistencePort(){

        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort(ICategoryPersistencePort categoryPersistencePort)
    {
        return new CategoryUseCase(categoryPersistencePort);
    }

    @Bean
    IBrandPersistencePort brandPersistencePort(){
        return new BrandJpaAdapter(brandRepository, brandEntityMapper);
    }

    @Bean
    IBrandServicePort brandServicePort(IBrandPersistencePort brandPersistencePort){
        return new BrandUseCase(brandPersistencePort);
    }

}
