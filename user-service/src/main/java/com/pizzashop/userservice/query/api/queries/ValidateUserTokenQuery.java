package com.pizzashop.userservice.query.api.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateUserTokenQuery {

    //private String userId;
    private String token;
}
