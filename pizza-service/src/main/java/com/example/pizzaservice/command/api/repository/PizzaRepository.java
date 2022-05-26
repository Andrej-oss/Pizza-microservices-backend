package com.example.pizzaservice.command.api.repository;

import com.example.pizzaservice.command.api.data.entity.Pizza;
import com.redis.om.spring.repository.RedisDocumentRepository;

public interface PizzaRepository extends RedisDocumentRepository<Pizza, String> {
}
