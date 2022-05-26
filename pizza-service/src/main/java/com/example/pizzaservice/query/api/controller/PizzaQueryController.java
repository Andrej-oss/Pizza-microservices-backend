package com.example.pizzaservice.query.api.controller;

import java.util.List;

import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pizzaservice.command.api.model.PizzaRestModel;
import com.example.pizzaservice.query.api.queries.GetPizzaQuery;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/pizza")
@Slf4j
public class PizzaQueryController {

    private final QueryGateway queryGateway;

    public PizzaQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    public List<PizzaRestModel> getAllPizza() {
        GetPizzaQuery pizzaQuery = new GetPizzaQuery();
        return this.queryGateway.query(pizzaQuery, ResponseTypes.multipleInstancesOf(PizzaRestModel.class)).join();
    }
}
