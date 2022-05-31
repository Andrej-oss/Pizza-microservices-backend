package com.example.mainregisterservice.command.api.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderCancelledEvent {

    private String orderId;
    private String orderStatus;
}
