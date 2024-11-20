package com.example.apigateway.config;

import com.example.apigateway.filter.LoggingFilter;
import com.example.apigateway.filter.TokenVerificationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class GatewayConfig {

    private final TokenVerificationFilter tokenVerificationFilter;

    private final LoggingFilter loggingFilter;

    public GatewayConfig(TokenVerificationFilter tokenVerificationFilter, LoggingFilter loggingFilter) {
        this.tokenVerificationFilter = tokenVerificationFilter;
        this.loggingFilter = loggingFilter;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("PRODUCT-MANAGEMENT-SERVICE", r -> r.path("/products/**")
                        .filters(f -> f.filter(loggingFilter.apply(new LoggingFilter.Config()))
                                       .filter(tokenVerificationFilter.apply(new TokenVerificationFilter.Config())))
                        .uri("lb://PRODUCT-MANAGEMENT-SERVICE"))
                .route("ORDER-MANAGEMENT-SERVICE", r -> r.path("/orders/**")
                        .filters(f -> f.filter(loggingFilter.apply(new LoggingFilter.Config()))
                                       .filter(tokenVerificationFilter.apply(new TokenVerificationFilter.Config())))
                        .uri("lb://ORDER-MANAGEMENT-SERVICE"))
                .route("USER-MANAGEMENT-SERVICE", r -> r.path("/auth/**", "/public/**", "/users/forgot-password", "/users/forgot-password/verify", "/users/verify-email")
                        .filters(f -> f.filter(loggingFilter.apply(new LoggingFilter.Config())))
                        .uri("lb://USER-MANAGEMENT-SERVICE"))
                .route("USER-MANAGEMENT-SERVICE-SECURE", r -> r.path("/users/**")
                        .filters(f -> f.filter(loggingFilter.apply(new LoggingFilter.Config()))
                                       .filter(tokenVerificationFilter.apply(new TokenVerificationFilter.Config())))
                        .uri("lb://USER-MANAGEMENT-SERVICE"))
                .build();
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("*");
        corsConfig.addAllowedMethod("*");
        corsConfig.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}