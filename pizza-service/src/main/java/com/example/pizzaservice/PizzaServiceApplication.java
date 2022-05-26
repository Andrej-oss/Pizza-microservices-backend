package com.example.pizzaservice;

import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.example.pizzaservice.command.api.exception.PizzaServiceEventErrorHandler;
import com.redis.om.spring.annotations.EnableRedisDocumentRepositories;

@SpringBootApplication
@EnableRedisDocumentRepositories(basePackages = "com.example.pizzaservice.*")
@EnableEurekaClient
public class PizzaServiceApplication {

    @Autowired
    public void configure(EventProcessingConfigurer configurer) {
        configurer.registerListenerInvocationErrorHandler("pizza",
                configuration -> new PizzaServiceEventErrorHandler());
    }

    public static void main(String[] args) {
        SpringApplication.run(PizzaServiceApplication.class, args);
    }
}
