package com.example.userservice.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.userservice.data.UserDto;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private final WebClient.Builder webClientBuilder;

    public AuthFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                throw new RuntimeException("Missing auth information");
            }
            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

            String[] splitAuthHeader = authHeader.split(" ");

            if (splitAuthHeader.length != 2 || !"Bearer".equals(splitAuthHeader[0])){
                throw new RuntimeException("Incorrect auth structure");
            }
            return this.webClientBuilder
                    .build()
                    .post()
                    .uri("http://user-service/users/validateToken?token" + splitAuthHeader[1])
                    .retrieve()
                    .bodyToMono(UserDto.class)
                    .map(userDto -> {
                        exchange.getRequest()
                                .mutate()
                                .header("x-auth-user-id", userDto.getId());
                        return exchange;
                    }).flatMap(chain::filter);
        });
    }

    public static class Config {

    }
}
