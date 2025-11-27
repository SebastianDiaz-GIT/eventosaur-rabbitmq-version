package com.softcaribbean.demo.rabbit.repository;

import com.softcaribbean.demo.rabbit.domain.entities.PolizaEstadoActualEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PolizaEstadoActualR2dbcRepository extends ReactiveCrudRepository<PolizaEstadoActualEntity, String> {
}
