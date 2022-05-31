package com.example.shipmentservice.command.api.repository;

import com.example.shipmentservice.command.api.entity.Shipment;
import com.redis.om.spring.repository.RedisDocumentRepository;

public interface ShipmentsRepository extends RedisDocumentRepository<Shipment, String> {
}
