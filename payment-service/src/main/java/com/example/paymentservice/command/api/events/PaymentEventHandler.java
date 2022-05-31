package com.example.paymentservice.command.api.events;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.mainregisterservice.command.api.events.PaymentCancelledEvent;
import com.example.mainregisterservice.command.api.events.PaymentProcessedEvent;
import com.example.paymentservice.command.api.entity.Payment;
import com.example.paymentservice.command.api.repository.PaymentRepository;
import com.pizzashop.orderservice.command.api.model.StatusOrder;

@Component
public class PaymentEventHandler {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentEventHandler(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @EventHandler
    public void on(PaymentProcessedEvent paymentProcessedEvent) {
        Payment payment = Payment.builder()
                .orderId(paymentProcessedEvent.getOrderId())
                .statusOrder(StatusOrder.COMPLETED)
                .timeStamp(LocalDateTime.now())
                .build();
        paymentRepository.save(payment);
    }

    @EventHandler
    public void on(PaymentCancelledEvent paymentCancelledEvent) {
        Payment payment = this.paymentRepository.findById(paymentCancelledEvent.getPaymentId()).get();
        payment.setStatusOrder(StatusOrder.valueOf(paymentCancelledEvent.getPaymentStatus()));
        this.paymentRepository.save(payment);
    }
}
