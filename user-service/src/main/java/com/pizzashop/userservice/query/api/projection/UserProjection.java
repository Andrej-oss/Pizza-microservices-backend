package com.pizzashop.userservice.query.api.projection;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pizzashop.userservice.command.api.data.UserDto;
import com.pizzashop.userservice.command.api.entity.Users;
import com.pizzashop.userservice.command.api.repository.UserRepository;
import com.pizzashop.userservice.query.api.data.UserResponseModel;
import com.pizzashop.userservice.query.api.queries.GetUserByIdQuery;
import com.pizzashop.userservice.query.api.queries.UserSignInQuery;
import com.pizzashop.userservice.query.api.queries.ValidateUserTokenQuery;
import com.pizzashop.userservice.query.api.service.UserQueryService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserProjection {

    final private UserQueryService userQueryService;

    @Autowired
    public UserProjection(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @QueryHandler
    public UserResponseModel getUserById(GetUserByIdQuery getUserByIdQuery) {
        log.info("Get User by Id Query was handled for user id: {}", getUserByIdQuery.getUserId());
        Users user = this.userQueryService.getUserById(getUserByIdQuery.getUserId());

        return UserResponseModel.builder()
                .id(user.getId())
                .age(user.getAge())
                .login(user.getLogin())
                .build();
    }

    @QueryHandler
    public UserDto validateUserToken(ValidateUserTokenQuery validateUserTokenQuery) throws Exception {
        log.info("Validate user token: {}", validateUserTokenQuery.getToken());

        return this.userQueryService.validateToken(validateUserTokenQuery.getToken());
    }

    @QueryHandler
    public UserDto signIn(UserSignInQuery userSignInQuery) throws Exception {
        log.info("Sign in user: {}", userSignInQuery.getLogin());

        return this.userQueryService.signIn(userSignInQuery.getLogin(), userSignInQuery.getPassword());
    }
}
