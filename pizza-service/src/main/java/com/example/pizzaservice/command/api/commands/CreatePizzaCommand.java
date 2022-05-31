package com.example.pizzaservice.command.api.commands;

import java.math.BigDecimal;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePizzaCommand {

    @TargetAggregateIdentifier
    private String id;

    private String name;
    private BigDecimal price;
}
