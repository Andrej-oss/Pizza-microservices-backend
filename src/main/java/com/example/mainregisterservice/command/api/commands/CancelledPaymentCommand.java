package com.example.mainregisterservice.command.api.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Value;

@Value
public class CancelledPaymentCommand {

    @TargetAggregateIdentifier
    private String paymentId;
    private String orderId;
    private String paymentStatus = "CANCELLED";
}
