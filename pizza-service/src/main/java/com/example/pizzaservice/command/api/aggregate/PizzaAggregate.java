package com.example.pizzaservice.command.api.aggregate;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.example.pizzaservice.command.api.commands.CreatePizzaCommand;
import com.example.pizzaservice.command.api.events.PizzaCreatedEvent;

import lombok.NoArgsConstructor;

@Aggregate
@NoArgsConstructor
public class PizzaAggregate {

    @AggregateIdentifier
    private String pizzaId;

    private String name;
    private BigDecimal price;

    @CommandHandler
    public PizzaAggregate(CreatePizzaCommand pizzaCommand) {
        PizzaCreatedEvent pizzaCreatedEvent = new PizzaCreatedEvent();
        BeanUtils.copyProperties(pizzaCommand, pizzaCreatedEvent);

        AggregateLifecycle.apply(pizzaCreatedEvent);
    }

    @EventSourcingHandler
    public void on(PizzaCreatedEvent pizzaCreatedEvent) {
        this.pizzaId = pizzaCreatedEvent.getId();
        this.name = pizzaCreatedEvent.getName();
        this.price = pizzaCreatedEvent.getPrice();
    }
}
