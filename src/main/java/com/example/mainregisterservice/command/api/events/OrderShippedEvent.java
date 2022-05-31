package com.example.mainregisterservice.command.api.events;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderShippedEvent {

    private String shipmentId;
    private String orderId;
    private String shipmentStatus;
}
