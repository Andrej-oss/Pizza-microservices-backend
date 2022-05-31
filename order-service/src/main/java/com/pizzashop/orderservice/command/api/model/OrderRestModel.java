package com.pizzashop.orderservice.command.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRestModel {

    private String pizzaId;
    private String userId;
    private String addressId;
    private Integer quantity;
}
