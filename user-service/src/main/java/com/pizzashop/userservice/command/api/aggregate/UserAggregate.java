package com.pizzashop.userservice.command.api.aggregate;

import java.time.LocalDate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.pizzashop.userservice.command.api.commands.CreateUserCommand;
import com.pizzashop.userservice.command.api.events.UserCreatedEvent;

import lombok.NoArgsConstructor;

@Aggregate
@NoArgsConstructor
public class UserAggregate {

    @AggregateIdentifier
    private String userId;
    private String login;
    private String password;
    private long age;

    @CommandHandler
    public UserAggregate(CreateUserCommand createUserCommand) {
        //todo validate command
        UserCreatedEvent userCreatedEvent = new UserCreatedEvent();
        BeanUtils.copyProperties(createUserCommand, userCreatedEvent);

        AggregateLifecycle.apply(userCreatedEvent);
    }

    @EventSourcingHandler
    public void on(UserCreatedEvent userCreatedEvent) {
        this.userId = userCreatedEvent.getUserId();
        this.login = userCreatedEvent.getLogin();
        this.password = userCreatedEvent.getPassword();
        this.age = userCreatedEvent.getAge();
    }
}
