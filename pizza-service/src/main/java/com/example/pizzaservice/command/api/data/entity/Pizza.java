package com.example.pizzaservice.command.api.data.entity;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Builder
@Data
@Document
public class Pizza {

    @Id
    @Indexed
    private String id;

    @Indexed
    @NonNull
    private String name;

    @Indexed
    @NonNull
    private BigDecimal price;
}
