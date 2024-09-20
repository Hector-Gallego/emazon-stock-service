package com.emazon.emazonstockservice.configuration.security.config;


import com.emazon.emazonstockservice.configuration.security.constants.ApiEndPointsConstants;
import com.emazon.emazonstockservice.configuration.security.constants.RoleNameConstants;
import com.emazon.emazonstockservice.configuration.security.constants.SecurityConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RsaKeyProperties rsaKeys;

    public SecurityConfig(RsaKeyProperties rsaKeys) {
        this.rsaKeys = rsaKeys;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(HttpMethod.PUT,
                                ApiEndPointsConstants.API_STOCK_URI)
                        .hasAnyAuthority(
                                RoleNameConstants.WAREHOUSE_ASSISTANT)

                        .requestMatchers(HttpMethod.POST,
                                ApiEndPointsConstants.API_CATEGORY_URI,
                                ApiEndPointsConstants.API_BRAND_URI,
                                ApiEndPointsConstants.API_ARTICLE_URI)
                        .hasAuthority(RoleNameConstants.ADMIN)

                        .requestMatchers(HttpMethod.GET,
                                ApiEndPointsConstants.API_CATEGORY_URI,
                                ApiEndPointsConstants.API_BRAND_URI,
                                ApiEndPointsConstants.API_ARTICLE_URI)
                        .hasAnyAuthority(
                                RoleNameConstants.ADMIN,
                                RoleNameConstants.CLIENT,
                                RoleNameConstants.WAREHOUSE_ASSISTANT)

                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> {
                            jwt.jwtAuthenticationConverter(jwtAuthenticationConverter());
                            jwt.decoder(jwtDecoder());
                        })
                )
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler()));

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){

        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(SecurityConstants.CLAIM_FIELD_NAME);
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(SecurityConstants.EMPTY_FIELD);

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;

    }
    @Bean
    JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }


}
