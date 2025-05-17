package com.example.controllers;

import com.example.model.Payment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
public class PaymentsController {

    private static Logger logger = Logger.getLogger(PaymentsController.class.getName());

    @PostMapping("/payment")
    public ResponseEntity<Payment> createPayment(
            @RequestHeader String requestId,
            @RequestBody Payment payment
    ) {
        logger.info("Received request with ID " + requestId +
                " ;Payment Amount: " + payment.getAmount());

        payment.setId(UUID.randomUUID().toString());

        return ResponseEntity
                .status(HttpStatus.OK)
                .header("requestId", requestId)
                .body(payment);
    }

//    @GetMapping(value = "/payments/{userId}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @GetMapping("/payments/{userId}")
    public Flux<Payment> getPaymentsByUserId(@PathVariable String userId) {
        logger.info("Received request to fetch payments for user " + userId);
        // TODO: integrate with service/repository to fetch payments by userId
        return Flux
                .range(0, 10)
                .delayElements(Duration.ofSeconds(1))
                .map(i -> {
                    Payment payment = new Payment();
                    payment.setId(userId + "--" + i + UUID.randomUUID().toString());
                    payment.setAmount(999.9);
                    return payment;
                })
                ;
    }

}
