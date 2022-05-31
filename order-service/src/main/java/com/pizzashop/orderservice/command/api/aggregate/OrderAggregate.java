package com.pizzashop.orderservice.command.api.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.example.mainregisterservice.command.api.commands.CancelledOrderCommand;
import com.example.mainregisterservice.command.api.commands.CompleteOrderCommand;
import com.example.mainregisterservice.command.api.events.OrderCancelledEvent;
import com.example.mainregisterservice.command.api.events.OrderCompletedEvent;
import com.pizzashop.orderservice.command.api.commands.CreateOrderCommand;
import com.pizzashop.orderservice.command.api.events.OrderCreatedEvent;
import com.pizzashop.orderservice.command.api.model.StatusOrder;

import lombok.NoArgsConstructor;

@Aggregate
@NoArgsConstructor
public class OrderAggregate {

    @AggregateIdentifier
    private String id;

    private String pizzaId;
    private String userId;
    private String addressId;
    private Integer quantity;
    private StatusOrder orderStatus;

    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand) {
        //todo validate command
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        BeanUtils.copyProperties(createOrderCommand, orderCreatedEvent);
        AggregateLifecycle.apply(orderCreatedEvent);
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent orderCreatedEvent) {
        this.id = orderCreatedEvent.getId();
        this.userId = orderCreatedEvent.getUserId();
        this.pizzaId = orderCreatedEvent.getPizzaId();
        this.addressId = orderCreatedEvent.getAddressId();
        this.quantity = orderCreatedEvent.getQuantity();
        this.orderStatus = orderCreatedEvent.getOrderStatus();
    }

    @CommandHandler
    public void handle(CompleteOrderCommand completeOrderCommand) {
        OrderCompletedEvent completedEvent = OrderCompletedEvent.builder()
                .orderId(completeOrderCommand.getOrderId())
                .orderStatus(completeOrderCommand.getOrderStatus())
                .build();

        AggregateLifecycle.apply(completedEvent);
    }

    @EventSourcingHandler
    public void on(OrderCompletedEvent orderCompletedEvent) {
        this.orderStatus = StatusOrder.valueOf(orderCompletedEvent.getOrderStatus());
    }

    @CommandHandler
    public void handle(CancelledOrderCommand cancelledOrderCommand) {
        OrderCancelledEvent cancelledEvent = OrderCancelledEvent.builder()
                .orderId(cancelledOrderCommand.getOrderId())
                .orderStatus(cancelledOrderCommand.getOrderStatus())
                .build();

        AggregateLifecycle.apply(cancelledEvent);
    }
    @EventSourcingHandler
    public void on(OrderCancelledEvent orderCancelledEvent) {
        this.orderStatus = StatusOrder.valueOf(orderCancelledEvent.getOrderStatus());
    }
}
