package com.pizzashop.orderservice.command.api.controller;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pizzashop.orderservice.command.api.commands.CreateOrderCommand;
import com.pizzashop.orderservice.command.api.model.OrderRestModel;
import com.pizzashop.orderservice.command.api.model.StatusOrder;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderCommandController {

    private final CommandGateway commandGateway;

    @Autowired
    public OrderCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String createOrder(@RequestBody OrderRestModel orderRestModel) {
        CreateOrderCommand orderCommand = CreateOrderCommand.builder()
                .id(UUID.randomUUID().toString())
                .pizzaId(orderRestModel.getPizzaId())
                .addressId(orderRestModel.getAddressId())
                .quantity(orderRestModel.getQuantity())
                .orderStatus(StatusOrder.CREATED)
                .build();
        return this.commandGateway.sendAndWait(orderCommand);
    }
}
