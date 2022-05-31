package com.example.paymentservice.command.api.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.example.mainregisterservice.command.api.commands.CancelledPaymentCommand;
import com.example.mainregisterservice.command.api.commands.ValidatePaymentCommand;
import com.example.mainregisterservice.command.api.events.PaymentCancelledEvent;
import com.example.mainregisterservice.command.api.events.PaymentProcessedEvent;
import com.pizzashop.orderservice.command.api.model.StatusOrder;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aggregate
@NoArgsConstructor
@Slf4j
public class PaymentAggregate {

    @AggregateIdentifier
    private String paymentId;
    private String orderId;
    private StatusOrder statusOrder;

    @CommandHandler
    public PaymentAggregate(ValidatePaymentCommand paymentCommand) {
        //todo validate payment details
        //if success publish Payment Processed event
        log.info("Executing ValidatePaymentCommand for OrderId: {} and PaymantId: {}", paymentCommand.getOrderId(),
                paymentCommand.getPaymentId());

        PaymentProcessedEvent paymentProcessedEvent = new PaymentProcessedEvent(paymentCommand.getPaymentId(),
                paymentCommand.getOrderId());

        AggregateLifecycle.apply(paymentProcessedEvent);
        log.info("ProcessedPaymentEvent applied");
    }

    @EventSourcingHandler
    public void on(PaymentProcessedEvent paymentProcessedEvent) {
        this.paymentId = paymentProcessedEvent.getPaymentId();
        this.orderId = paymentProcessedEvent.getOrderId();
        this.statusOrder = StatusOrder.PROCESSED;
    }

    @CommandHandler
    public void handle(CancelledPaymentCommand cancelledPaymentCommand) {
        PaymentCancelledEvent paymentCancelledEvent = PaymentCancelledEvent.builder()
                .paymentId(cancelledPaymentCommand.getPaymentId())
                .orderId(cancelledPaymentCommand.getOrderId())
                .paymentStatus(cancelledPaymentCommand.getPaymentStatus())
                .build();
        AggregateLifecycle.apply(paymentCancelledEvent);
    }

    @EventSourcingHandler
    public void on(PaymentCancelledEvent paymentCancelledEvent) {
        this.statusOrder = StatusOrder.valueOf(paymentCancelledEvent.getPaymentStatus());
    }
}
