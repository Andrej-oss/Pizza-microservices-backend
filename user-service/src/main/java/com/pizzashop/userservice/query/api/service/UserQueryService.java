package com.pizzashop.userservice.query.api.service;

import com.pizzashop.userservice.command.api.data.UserDto;
import com.pizzashop.userservice.command.api.entity.Users;
import com.pizzashop.userservice.query.api.queries.ValidateUserTokenQuery;

public interface UserQueryService {

    UserDto validateToken(String token) throws Exception;
    Users getUserById(String id);

    UserDto signIn(String login, char[] password) throws Exception;
}
