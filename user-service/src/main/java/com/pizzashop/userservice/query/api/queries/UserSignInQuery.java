package com.pizzashop.userservice.query.api.queries;

import com.pizzashop.userservice.command.api.data.CredentialsDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class UserSignInQuery extends CredentialsDto {

    public UserSignInQuery(String login, char[] password) {
        super(login, password);
    }

    public UserSignInQuery() {
    }
}
