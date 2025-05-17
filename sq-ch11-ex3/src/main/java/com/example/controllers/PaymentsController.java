package com.example.controllers;

import com.example.model.Payment;
import com.example.proxy.PaymentsProxy;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
public class PaymentsController {

    private final PaymentsProxy paymentsProxy;

    public PaymentsController(PaymentsProxy paymentsProxy) {
        this.paymentsProxy = paymentsProxy;
    }

    @PostMapping("/payment")
    public Mono<Payment> createPayment(
            @RequestBody Payment payment
    ) {
        String requestId = UUID.randomUUID().toString();
        return paymentsProxy.createPayment(requestId, payment);
    }


    //    @GetMapping(value = "/payments/{userId}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @GetMapping("/payments/{userId}")
    public Flux<Payment> getPaymentsByUserId(@PathVariable String userId) {
        return paymentsProxy.getPaymentsByUserId(userId);
    }

}
