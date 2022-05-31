package com.pizzashop.orderservice.command.api.entity;

import org.springframework.data.annotation.Id;

import com.pizzashop.orderservice.command.api.model.StatusOrder;
import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Order {

    @Id
    @Indexed
    private String id;

    @Indexed
    @NonNull
    private String userId;

    @Indexed
    @NonNull
    private String pizzaId;

    @Indexed
    @NonNull
    private String addressId;

    @Indexed
    @NonNull
    private Integer quantity;

    @Indexed
    @NonNull
    private StatusOrder statusOrder;
}
