package com.example.mainregisterservice.command.api.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentCancelledEvent {

    private String paymentId;
    private String orderId;
    private String paymentStatus;
}
