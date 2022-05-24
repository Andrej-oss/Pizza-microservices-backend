package com.example.pizzaservice.command.api.model;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PizzaRestModel {

    private String name;
    private BigDecimal price;
}
