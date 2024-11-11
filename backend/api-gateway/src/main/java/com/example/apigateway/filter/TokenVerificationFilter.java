package com.example.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Collections;
import java.util.List;

@Component
public class TokenVerificationFilter extends AbstractGatewayFilterFactory<TokenVerificationFilter.Config> {

    @Autowired
    private WebClient.Builder webClientBuilder;


    //string for verifying the token
    private  final String VERIFY_TOKEN = "http://localhost:8081/auth/verify-token";

    private static final List<String> EXCLUDED_PATHS = List.of(
            "/auth/.*", "/public/.*", "/users/forgot-password",
            "/users/forgot-password/verify", "/users/verify-email"
    );

    public TokenVerificationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();
            if (EXCLUDED_PATHS.stream().anyMatch(path::matches)) {
                return chain.filter(exchange);
            }

            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            } else {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // Check whether the token is valid or not
            return webClientBuilder.build()
                    .post()
                    .uri(VERIFY_TOKEN)
                    .bodyValue(Collections.singletonMap("token", token))
                    .exchangeToMono(clientResponse -> {
                        if (clientResponse.statusCode().is2xxSuccessful()) {
                            return chain.filter(exchange);
                        } else {
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            return exchange.getResponse().setComplete();
                        }
                    });
        };
    }

    public static class Config {
        // Configuration properties if needed
    }
}