package com.pizzashop.userservice.command.api.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CredentialsDto {

    private String login;
    private char[] password;
}
