package com.softcaribbean.demo.rabbit.repository;

import com.softcaribbean.demo.rabbit.domain.entities.PolizaEventEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PolizaEventR2dbcRepository extends ReactiveCrudRepository<PolizaEventEntity, Long> {
    Flux<PolizaEventEntity> findAllByIdPoliza(String idPoliza);
}
