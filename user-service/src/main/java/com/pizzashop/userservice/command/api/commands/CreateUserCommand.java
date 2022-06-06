package com.pizzashop.userservice.command.api.commands;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserCommand {

    @TargetAggregateIdentifier
    private String userId;
    private String login;
    private String password;
    private long age;
}
