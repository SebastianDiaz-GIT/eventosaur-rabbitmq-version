package com.softcaribbean.demo.rabbit.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softcaribbean.demo.rabbit.domain.dtos.PolizaEvent;
import com.softcaribbean.demo.rabbit.repository.PolizaEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
@Log4j2
@RequiredArgsConstructor
public class PolizaEventListener {
    private final PolizaEventRepository polizaEventRepository;
    private final Sinks.Many<PolizaEvent> sink = Sinks.many().multicast().onBackpressureBuffer();

    @RabbitListener(queues = "poliza.events.queue")
    public void onPolizaEvent(String eventJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            PolizaEvent event = mapper.readValue(eventJson, PolizaEvent.class);
            polizaEventRepository.saveEvent(event).subscribe();
            sink.tryEmitNext(event);
            log.info("Evento recibido: {}", event);
        } catch (Exception e) {
            log.error("Error deserializando evento: {}", eventJson, e);
        }
    }

    public Flux<PolizaEvent> streamPolizaEvents() {
        return sink.asFlux();
    }

    public Flux<PolizaEvent> getTrazabilidad(String idPoliza) {
        return polizaEventRepository.getTrazabilidad(idPoliza)
                .map(entity -> {
                    PolizaEvent dto = new PolizaEvent();
                    dto.setIdPoliza(entity.getIdPoliza());
                    dto.setExtraData(entity.getExtraData());
                    return dto;
                });
    }

    public Mono<PolizaEvent> getEstadoActual(String idPoliza) {
        return polizaEventRepository.getEstadoActual(idPoliza)
                .map(entity -> {
                    PolizaEvent dto = new PolizaEvent();
                    dto.setIdPoliza(entity.getIdPoliza());
                    dto.setExtraData(entity.getEstado());
                    return dto;
                });
    }
}
