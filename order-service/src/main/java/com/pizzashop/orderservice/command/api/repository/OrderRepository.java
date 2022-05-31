package com.pizzashop.orderservice.command.api.repository;

import com.pizzashop.orderservice.command.api.entity.Order;
import com.redis.om.spring.repository.RedisDocumentRepository;

public interface OrderRepository extends RedisDocumentRepository<Order, String> {
}
