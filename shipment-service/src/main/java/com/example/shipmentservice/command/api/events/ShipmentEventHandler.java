package com.example.shipmentservice.command.api.events;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.mainregisterservice.command.api.events.OrderShippedEvent;
import com.example.shipmentservice.command.api.entity.Shipment;
import com.example.shipmentservice.command.api.repository.ShipmentsRepository;

@Component
public class ShipmentEventHandler {

    private final ShipmentsRepository shipmentsRepository;

    @Autowired
    public ShipmentEventHandler(ShipmentsRepository shipmentsRepository) {
        this.shipmentsRepository = shipmentsRepository;
    }

    @EventHandler
    public void on(OrderShippedEvent orderShippedEvent) {
        Shipment shipment = Shipment.builder()
                .orderId(orderShippedEvent.getOrderId())
                .shipmentStatus(orderShippedEvent.getShipmentStatus())
                .build();

        this.shipmentsRepository.save(shipment);
    }
}
