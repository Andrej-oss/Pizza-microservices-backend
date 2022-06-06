package com.pizzashop.userservice.command.api.service;

import com.pizzashop.userservice.command.api.commands.CreateUserCommand;
import com.pizzashop.userservice.command.api.data.UserDto;
import com.pizzashop.userservice.command.api.data.UserRestModel;

public interface UserService {

    CreateUserCommand addUser(UserRestModel userRestModel);
    String getToken(String userName);
//    UserDto signUp(UserRestModel userRestModel) throws Exception;
}
