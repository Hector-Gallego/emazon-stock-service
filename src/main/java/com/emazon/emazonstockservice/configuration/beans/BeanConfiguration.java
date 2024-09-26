package com.emazon.emazonstockservice.configuration.beans;


import com.emazon.emazonstockservice.domain.ports.api.ArticleServicePort;
import com.emazon.emazonstockservice.domain.ports.api.BrandServicePort;
import com.emazon.emazonstockservice.domain.ports.api.CategoryServicePort;
import com.emazon.emazonstockservice.domain.ports.api.StockServicePort;
import com.emazon.emazonstockservice.domain.ports.spi.ArticlePersistencePort;
import com.emazon.emazonstockservice.domain.ports.spi.BrandPersistencePort;
import com.emazon.emazonstockservice.domain.ports.spi.CategoryPersistencePort;
import com.emazon.emazonstockservice.domain.ports.spi.StockPersistencePort;
import com.emazon.emazonstockservice.domain.usecase.ArticleUseCase;
import com.emazon.emazonstockservice.domain.usecase.BrandUseCase;
import com.emazon.emazonstockservice.domain.usecase.CategoryUseCase;
import com.emazon.emazonstockservice.domain.usecase.StockUseCase;
import com.emazon.emazonstockservice.domain.validator.StockValidator;
import com.emazon.emazonstockservice.ports.driven.mysql.adapter.ArticleJpaAdapter;
import com.emazon.emazonstockservice.ports.driven.mysql.adapter.BrandJpaAdapter;
import com.emazon.emazonstockservice.ports.driven.mysql.adapter.CategoryJpaAdapter;
import com.emazon.emazonstockservice.ports.driven.mysql.adapter.StockJpaAdapter;
import com.emazon.emazonstockservice.ports.driven.mysql.mapper.BrandEntityMapper;
import com.emazon.emazonstockservice.ports.driven.mysql.mapper.CategoryEntityMapper;
import com.emazon.emazonstockservice.ports.driven.mysql.mapper.ArticleEntityMapper;
import com.emazon.emazonstockservice.ports.driven.mysql.repository.IArticleRepository;
import com.emazon.emazonstockservice.ports.driven.mysql.repository.IBrandRepository;
import com.emazon.emazonstockservice.ports.driven.mysql.repository.ICategoryRepository;
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

    @Bean
    StockServicePort stockServicePort(StockPersistencePort stockPersistencePort,
                                      ArticlePersistencePort articlePersistencePort,
                                      StockValidator stockValidator){
        return new StockUseCase(stockPersistencePort,articlePersistencePort,stockValidator);
    }

    @Bean
    StockPersistencePort stockPersistencePort(IArticleRepository articleRepository){
        return new StockJpaAdapter(articleRepository);
    }


    @Bean
    StockValidator stockValidator(StockPersistencePort stockPersistencePort, ArticlePersistencePort articlePersistencePort){
        return new StockValidator( stockPersistencePort, articlePersistencePort);
    }

}
