package com.example.pizzaservice.query.api.projection;

import java.util.List;
import java.util.stream.Collectors;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.pizzaservice.command.api.data.entity.Pizza;
import com.example.pizzaservice.command.api.model.PizzaRestModel;
import com.example.pizzaservice.command.api.repository.PizzaRepository;
import com.example.pizzaservice.query.api.queries.GetPizzaQuery;

@Component
public class PizzaProjection {

    private final PizzaRepository pizzaRepository;

    @Autowired
    public PizzaProjection(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @QueryHandler
    public Iterable<PizzaRestModel> handle(GetPizzaQuery pizzaQuery) {
        List<Pizza> pizzas = (List<Pizza>) pizzaRepository.findAll();
        return pizzas.stream()
                .map(pizza -> PizzaRestModel.builder().name(pizza.getName()).price(pizza.getPrice()).build())
                .collect(Collectors.toList());
    }
}
