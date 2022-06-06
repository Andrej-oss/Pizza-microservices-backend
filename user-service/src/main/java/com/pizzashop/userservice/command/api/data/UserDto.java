package com.pizzashop.userservice.command.api.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private String id;
    private String name;
    private String token;
}
