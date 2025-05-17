package com.example.proxy;

import com.example.model.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
public class PaymentsProxy {

    private final WebClient webClient;

    @Value("${name.service.url}")
    private String url;

    public PaymentsProxy(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Payment> createPayment(String requestId, Payment payment) {
        return webClient.post()
                .uri(url + "/payment")
                .header("requestId", requestId)
                .body(Mono.just(payment), Payment.class)
                .retrieve()
                .bodyToMono(Payment.class);
    }


    /**
     * Consume GET /payments/{userId}, returning a Flux of Payment.
     */
    public Flux<Payment> getPaymentsByUserId(String userId) {
        return webClient.get()
                .uri(url + "/payments/{userId}", userId)
                .retrieve()
                .bodyToFlux(Payment.class)
                .map(payment -> {
                    payment.setDateTime(LocalDateTime.now());
                    System.out.println(payment.getDateTime().toString());
                    return payment;
                })
                ;
    }
}
