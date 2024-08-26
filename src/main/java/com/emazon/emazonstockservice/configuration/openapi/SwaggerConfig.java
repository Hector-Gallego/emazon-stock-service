package com.emazon.emazonstockservice.configuration.openapi;


import com.emazon.emazonstockservice.configuration.util.ConfigurationConstants;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title(ConfigurationConstants.OPENAPI_TITTLE)
                        .version(ConfigurationConstants.OPENAPI_VERSION)
                        .description(ConfigurationConstants.OPENAPI_DESCRIPTION)

                );

    }
}
