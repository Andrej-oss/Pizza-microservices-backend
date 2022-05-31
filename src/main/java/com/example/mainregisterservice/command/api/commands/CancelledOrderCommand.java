package com.example.mainregisterservice.command.api.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Value;

@Value
public class CancelledOrderCommand {

    @TargetAggregateIdentifier
    private String orderId;
    private String orderStatus = "CANCELLED";
}
