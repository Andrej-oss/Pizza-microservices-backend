package com.example.pizzaservice.command.api.events;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.pizzaservice.command.api.data.entity.Pizza;
import com.example.pizzaservice.command.api.repository.PizzaRepository;

@Component
@ProcessingGroup("pizza")
public class PizzaEventsHandler {

    private final PizzaRepository pizzaRepository;

    @Autowired
    public PizzaEventsHandler(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @EventHandler
    public void on(PizzaCreatedEvent pizzaEvent) throws Exception {
        Pizza pizza = Pizza.builder().name(pizzaEvent.getName()).price(pizzaEvent.getPrice()).build();
        this.pizzaRepository.save(pizza);
        throw new Exception("exception");
    }

    @ExceptionHandler
    public void handle(Exception e) throws Exception {
        throw e;
    }
}
