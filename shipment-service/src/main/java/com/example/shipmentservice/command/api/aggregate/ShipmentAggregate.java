package com.example.shipmentservice.command.api.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.example.mainregisterservice.command.api.commands.ShipOrderCommand;
import com.example.mainregisterservice.command.api.events.OrderShippedEvent;
import com.pizzashop.orderservice.command.api.model.StatusOrder;

import lombok.NoArgsConstructor;

@Aggregate
@NoArgsConstructor
public class ShipmentAggregate {

    @AggregateIdentifier
    private String shipmentId;
    private String orderId;
    private String shipmentStatus;

    @CommandHandler
    public ShipmentAggregate(ShipOrderCommand shipmentOrderCommand) {
        //todo validate command
        // publish Order Shipment Event

        OrderShippedEvent orderShippedEvent = OrderShippedEvent.builder()
                .shipmentId(shipmentOrderCommand.getShipmentId())
                .orderId(shipmentOrderCommand.getOrderId())
                .shipmentStatus(StatusOrder.COMPLETED.toString())
                .build();

        AggregateLifecycle.apply(orderShippedEvent);
    }

    @EventSourcingHandler
    public void on(OrderShippedEvent orderShippedEvent) {
        this.orderId = orderShippedEvent.getOrderId();
        this.shipmentId = orderShippedEvent.getShipmentId();
        this.shipmentStatus = orderShippedEvent.getShipmentStatus();
    }
}
