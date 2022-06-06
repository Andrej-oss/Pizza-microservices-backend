package com.pizzashop.userservice.query.api.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseModel {

    private String id;
    private String login;
    private Long age;
}
