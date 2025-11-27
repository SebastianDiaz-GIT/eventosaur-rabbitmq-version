package com.softcaribbean.demo.rabbit.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softcaribbean.demo.rabbit.domain.dtos.PolizaEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RabbitEventGateway {
    private final RabbitTemplate rabbitTemplate;
    private static final String EXCHANGE = "poliza.events.exchange";
    private static final String ROUTING_KEY = "poliza.events.key";

    public Mono<PolizaEvent> sendEvent(PolizaEvent event) {
        return Mono.fromCallable(() -> {
            // Serializar el evento a JSON antes de enviarlo
            String json = new ObjectMapper().writeValueAsString(event);
            rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, json);
            return event;
        });
    }
}
