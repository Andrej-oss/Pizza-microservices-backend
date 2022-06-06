package com.pizzashop.userservice.command.api.events;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreatedEvent {

    private String userId;
    private String login;
    private String password;
    private long age;
}
