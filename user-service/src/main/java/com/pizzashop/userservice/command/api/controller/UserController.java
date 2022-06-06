package com.pizzashop.userservice.command.api.controller;

import javax.validation.Valid;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pizzashop.userservice.command.api.commands.CreateUserCommand;
import com.pizzashop.userservice.command.api.data.CredentialsDto;
import com.pizzashop.userservice.command.api.data.UserDto;
import com.pizzashop.userservice.command.api.data.UserRestModel;
import com.pizzashop.userservice.command.api.entity.Users;
import com.pizzashop.userservice.command.api.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final CommandGateway commandGateway;
    private final UserService userService;

    @Autowired
    public UserController(CommandGateway commandGateway, UserService userService) {
        this.commandGateway = commandGateway;
        this.userService = userService;
    }

    @PostMapping("/signUp")
    public String addUser(@RequestBody @Valid UserRestModel userRestModel) {
        CreateUserCommand createUserCommand = this.userService.addUser(userRestModel);

        this.commandGateway.sendAndWait(createUserCommand);

        return this.userService.getToken(userRestModel.getLogin());
    }

}
