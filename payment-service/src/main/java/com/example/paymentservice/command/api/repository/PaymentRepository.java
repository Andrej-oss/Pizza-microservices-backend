package com.example.paymentservice.command.api.repository;

import com.example.paymentservice.command.api.entity.Payment;
import com.redis.om.spring.repository.RedisDocumentRepository;

public interface PaymentRepository extends RedisDocumentRepository<Payment, String> {
}
