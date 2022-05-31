package com.pizzashop.orderservice.command.api.events;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.mainregisterservice.command.api.events.OrderCancelledEvent;
import com.example.mainregisterservice.command.api.events.OrderCompletedEvent;
import com.pizzashop.orderservice.command.api.entity.Order;
import com.pizzashop.orderservice.command.api.model.StatusOrder;
import com.pizzashop.orderservice.command.api.repository.OrderRepository;

@Component
public class OrderEventsHandler {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderEventsHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @EventHandler
    public void on(OrderCreatedEvent orderCreatedEvent) {
        Order order = Order.builder()
                .userId(orderCreatedEvent.getUserId())
                .pizzaId(orderCreatedEvent.getPizzaId())
                .addressId(orderCreatedEvent.getAddressId())
                .quantity(orderCreatedEvent.getQuantity())
                .statusOrder(orderCreatedEvent.getOrderStatus())
                .build();
        this.orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderCompletedEvent orderCompletedEvent) {
        Order order = this.orderRepository.findById(orderCompletedEvent.getOrderId()).get();
        order.setStatusOrder(StatusOrder.valueOf(orderCompletedEvent.getOrderStatus()));
        this.orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderCancelledEvent orderCancelledEvent) {
        Order order = this.orderRepository.findById(orderCancelledEvent.getOrderId()).get();
        order.setStatusOrder(StatusOrder.valueOf(orderCancelledEvent.getOrderStatus()));
        this.orderRepository.save(order);
    }
}
