package com.pizzashop.userservice.query.api.controller;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pizzashop.userservice.command.api.data.CredentialsDto;
import com.pizzashop.userservice.command.api.data.UserDto;
import com.pizzashop.userservice.query.api.data.UserResponseModel;
import com.pizzashop.userservice.query.api.queries.GetUserByIdQuery;
import com.pizzashop.userservice.query.api.queries.UserSignInQuery;
import com.pizzashop.userservice.query.api.queries.ValidateUserTokenQuery;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class QueryUserController {

    private final QueryGateway queryGateway;

    public QueryUserController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/{userId}")
    public UserResponseModel getUserById(@PathVariable String userId) {
        GetUserByIdQuery getUserByIdQuery = new GetUserByIdQuery(userId);

        return this.queryGateway.query(getUserByIdQuery,
                ResponseTypes.instanceOf(UserResponseModel.class)).join();
    }

    @GetMapping("/validateToken")
    public UserDto validateToken(@RequestParam String token) {
        log.info("Validate token: {}", token);
        ValidateUserTokenQuery validateUserTokenQuery = new ValidateUserTokenQuery(token);

        return this.queryGateway.query(validateUserTokenQuery, ResponseTypes.instanceOf(UserDto.class)).join();
    }

    @PostMapping("/signIn")
    @ResponseStatus(HttpStatus.OK)
    public UserDto sigIn(@RequestBody CredentialsDto credentialsDto) {
        UserSignInQuery userSignInQuery = new UserSignInQuery(credentialsDto.getLogin(), credentialsDto.getPassword());

        return this.queryGateway.query(userSignInQuery, ResponseTypes.instanceOf(UserDto.class)).join();
    }
}
