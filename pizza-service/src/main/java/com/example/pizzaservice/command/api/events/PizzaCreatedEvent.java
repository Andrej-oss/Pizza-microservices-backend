package com.example.pizzaservice.command.api.events;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PizzaCreatedEvent {

    private String id;
    private String name;
    private BigDecimal price;
}
