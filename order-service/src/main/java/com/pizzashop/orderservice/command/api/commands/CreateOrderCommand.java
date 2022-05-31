package com.pizzashop.orderservice.command.api.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.pizzashop.orderservice.command.api.model.StatusOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class CreateOrderCommand {

    @TargetAggregateIdentifier
    private String id;

    private String pizzaId;
    private String userId;
    private String addressId;
    private Integer quantity;
    private StatusOrder orderStatus;
}
