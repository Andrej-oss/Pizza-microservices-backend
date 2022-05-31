package com.example.paymentservice.command.api.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import com.pizzashop.orderservice.command.api.model.StatusOrder;
import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Payment {

    @Id
    @Indexed
    private String paymentId;

    @Indexed
    @NonNull
    private String orderId;

    @Indexed
    @NonNull
    private LocalDateTime timeStamp;

    @Indexed
    @NonNull
    private StatusOrder statusOrder;
}
