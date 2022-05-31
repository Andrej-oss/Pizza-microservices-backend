package com.pizzashop.orderservice.command.api.events;

import com.pizzashop.orderservice.command.api.model.StatusOrder;

import lombok.Data;

@Data
public class OrderCreatedEvent {

    private String id;
    private String pizzaId;
    private String userId;
    private String addressId;
    private Integer quantity;
    private StatusOrder orderStatus;
}
