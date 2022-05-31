package com.example.shipmentservice.command.api.entity;

import org.springframework.data.annotation.Id;

import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipment {

    @Id
    @Indexed
    private String id;

    @Indexed
    @NonNull
    private String orderId;

    @Indexed
    @NonNull
    private String shipmentStatus;
}
