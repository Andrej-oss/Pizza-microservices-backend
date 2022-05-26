package com.example.pizzaservice.command.api.controller;

import java.math.BigDecimal;
import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pizzaservice.command.api.commands.CreatePizzaCommand;
import com.example.pizzaservice.command.api.model.PizzaRestModel;

@RestController
@RequestMapping("/pizza")
public class PizzaController {

    private final CommandGateway commandGateway;

    public PizzaController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String addPizza(@RequestBody PizzaRestModel pizzaModel) {

        CreatePizzaCommand pizzaCommand = CreatePizzaCommand.builder()
                .id(UUID.randomUUID().toString())
                .name(pizzaModel.getName())
                .price(pizzaModel.getPrice())
                .build();
        return commandGateway.sendAndWait(pizzaCommand);
    }
}
