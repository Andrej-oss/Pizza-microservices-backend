package com.example.userservice.projection;

import org.axonframework.queryhandling.QueryHandler;

import com.example.mainregisterservice.command.api.model.CardDetails;
import com.example.mainregisterservice.command.api.model.User;
import com.example.mainregisterservice.query.api.queries.GetUserDetailsQuery;

public class UserProjection {

    @QueryHandler
    public User getUserDetails(GetUserDetailsQuery getUserDetailsQuery) {
        CardDetails cardDetails = CardDetails.builder()
                .name("Joe Doe")
                .cardNumber("21314345434435435")
                .validUntilMonth(12)
                .validUntilYear(2023)
                .cvv(456)
                .build();
        return User.builder()
                .userId(getUserDetailsQuery.getUserId())
                .firstName("Joe")
                .lastName("Doe")
                .cardDetails(cardDetails)
                .build();
    }
}
