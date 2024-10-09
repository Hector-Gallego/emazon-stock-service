package com.emazon.emazonstockservice.configuration.security.config;


import com.emazon.emazonstockservice.configuration.exception.delegatehandle.DelegateAccessDeniedHandler;
import com.emazon.emazonstockservice.configuration.exception.delegatehandle.DelegateAuthenticationEntryPoint;
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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RsaKeyProperties rsaKeys;
    private final DelegateAuthenticationEntryPoint authenticationEntryPoint;
    private final DelegateAccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(RsaKeyProperties rsaKeys, DelegateAuthenticationEntryPoint authenticationEntryPoint, DelegateAccessDeniedHandler accessDeniedHandler) {
        this.rsaKeys = rsaKeys;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(HttpMethod.POST,
                                ApiEndPointsConstants.API_STOCK_URI,
                                ApiEndPointsConstants.API_STOCK_LIST_CART_URI)
                        .hasAuthority(RoleNameConstants.CLIENT)

                        .requestMatchers(HttpMethod.PUT,
                                ApiEndPointsConstants.API_STOCK_URI)
                        .hasAuthority(RoleNameConstants.WAREHOUSE_ASSISTANT)

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
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                );

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
