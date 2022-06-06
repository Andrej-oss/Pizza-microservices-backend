package com.pizzashop.userservice.command.api.events.handlers;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pizzashop.userservice.command.api.entity.Users;
import com.pizzashop.userservice.command.api.events.UserCreatedEvent;
import com.pizzashop.userservice.command.api.repository.UserRepository;
import com.pizzashop.userservice.command.api.service.impl.JwtService;

@Component
public class UserEventHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Autowired
    public UserEventHandler(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @EventHandler
    public void on(UserCreatedEvent userCreatedEvent) {
        Users user = Users.builder()
                .login(userCreatedEvent.getLogin())
                .password(userCreatedEvent.getPassword())
                .age(userCreatedEvent.getAge())
                .build();

        this.userRepository.save(user);
    }
}
