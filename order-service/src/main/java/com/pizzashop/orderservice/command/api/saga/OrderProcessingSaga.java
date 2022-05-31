package com.pizzashop.orderservice.command.api.saga;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.mainregisterservice.command.api.commands.CancelledOrderCommand;
import com.example.mainregisterservice.command.api.commands.CancelledPaymentCommand;
import com.example.mainregisterservice.command.api.commands.CompleteOrderCommand;
import com.example.mainregisterservice.command.api.commands.ShipOrderCommand;
import com.example.mainregisterservice.command.api.commands.ValidatePaymentCommand;
import com.example.mainregisterservice.command.api.events.OrderCancelledEvent;
import com.example.mainregisterservice.command.api.events.OrderCompletedEvent;
import com.example.mainregisterservice.command.api.events.OrderShippedEvent;
import com.example.mainregisterservice.command.api.events.PaymentCancelledEvent;
import com.example.mainregisterservice.command.api.events.PaymentProcessedEvent;
import com.example.mainregisterservice.command.api.model.User;
import com.example.mainregisterservice.query.api.queries.GetUserDetailsQuery;
import com.pizzashop.orderservice.command.api.events.OrderCreatedEvent;

import lombok.extern.slf4j.Slf4j;

@Saga
@Slf4j
public class OrderProcessingSaga {

    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    private void handle(OrderCreatedEvent orderCreatedEvent) {
        log.info("OrderCreatedEvent in Saga for Order id: {}", orderCreatedEvent.getId());

        GetUserDetailsQuery userDetailsQuery = new GetUserDetailsQuery(orderCreatedEvent.getUserId());

        User user = null;
        try {
            user = queryGateway.query(userDetailsQuery, ResponseTypes.instanceOf(User.class)).join();
        } catch (Exception e) {
            log.error(e.getMessage());
            this.cancelOrderCreate(orderCreatedEvent.getId());
        }

        ValidatePaymentCommand validatePaymentCommand = ValidatePaymentCommand.builder()
                .cardDetails(user.getCardDetails())
                .orderId(orderCreatedEvent.getId())
                .paymentId(UUID.randomUUID().toString())
                .build();

        this.commandGateway.sendAndWait(validatePaymentCommand);
    }

    private void cancelOrderCreate(String id) {
        CancelledOrderCommand cancelledOrderCommand = new CancelledOrderCommand(id);
        this.commandGateway.send(cancelledOrderCommand);
    }

    @SagaEventHandler(associationProperty = "orderId")
    private void handle(PaymentProcessedEvent paymentProcessedEvent) {
        log.info("PaymentProcessedEvent in Saga for PaymentId: {}", paymentProcessedEvent.getPaymentId());
        try {
            ShipOrderCommand shipOrderCommand = ShipOrderCommand.builder()
                    .shipmentId(UUID.randomUUID().toString())
                    .orderId(paymentProcessedEvent.getOrderId())
                    .build();

            commandGateway.send(shipOrderCommand);
        } catch (Exception e) {
            log.error(e.getMessage());
            this.cancelPaymentCreate(paymentProcessedEvent);
        }
    }

    private void cancelPaymentCreate(PaymentProcessedEvent paymentProcessedEvent) {
        CancelledPaymentCommand cancelledPaymentCommand = new CancelledPaymentCommand(
                paymentProcessedEvent.getOrderId(), paymentProcessedEvent.getPaymentId());
        this.commandGateway.send(cancelledPaymentCommand);
    }

    @SagaEventHandler(associationProperty = "orderId")
    private void handle(OrderShippedEvent orderShippedEvent) {
        log.info("Order Shipped Event was handled in Saga for orderId: {}", orderShippedEvent.getOrderId());

        try {
            CompleteOrderCommand completeOrderCommand = CompleteOrderCommand.builder()
                    .orderId(orderShippedEvent.getOrderId())
                    .orderStatus("APPROVED")
                    .build();
            commandGateway.send(completeOrderCommand);
        } catch (Exception e) {
            log.error(e.getMessage());
            //start compensating transaction
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    private void handle(OrderCompletedEvent orderCompletedEvent) {
        log.info("Order Competed Event was handled in Saga for orderId: {}", orderCompletedEvent.getOrderId());
    }

    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void handle(OrderCancelledEvent cancelledEvent) {
        log.info("Order Cancelled Event was handled in Saga for orderId: {}", cancelledEvent.getOrderId());
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentCancelledEvent paymentCancelledEvent) {
        log.info("Payment Cancelled Event was handled in Saga for orderId: {}", paymentCancelledEvent.getOrderId());
        this.cancelOrderCreate(paymentCancelledEvent.getOrderId());
    }
}
