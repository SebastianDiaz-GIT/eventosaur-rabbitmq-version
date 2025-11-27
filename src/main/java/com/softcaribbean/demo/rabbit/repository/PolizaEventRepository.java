package com.softcaribbean.demo.rabbit.repository;

import com.softcaribbean.demo.rabbit.domain.dtos.PolizaEvent;
import com.softcaribbean.demo.rabbit.domain.entities.PolizaEventEntity;
import com.softcaribbean.demo.rabbit.domain.entities.PolizaEstadoActualEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class PolizaEventRepository {
    private final PolizaEventR2dbcRepository eventRepo;
    private final PolizaEstadoActualR2dbcRepository estadoRepo;
    private final DatabaseClient databaseClient;

    @Autowired
    public PolizaEventRepository(PolizaEventR2dbcRepository eventRepo, PolizaEstadoActualR2dbcRepository estadoRepo, DatabaseClient databaseClient) {
        this.eventRepo = eventRepo;
        this.estadoRepo = estadoRepo;
        this.databaseClient = databaseClient;
    }

    public Mono<PolizaEventEntity> saveEvent(PolizaEvent event) {
        PolizaEventEntity entity = new PolizaEventEntity();
        entity.setId(String.valueOf(java.util.UUID.randomUUID()));
        entity.setIdPoliza(event.getIdPoliza());
        entity.setExtraData(event.getExtraData());
        return databaseClient.sql(
                        "INSERT INTO poliza_evento (id, id_poliza, extra_data) " +
                                "VALUES (:id, :id_poliza, :extra_data)")
                .bind("id", entity.getId())
                .bind("id_poliza", entity.getIdPoliza())
                .bind("extra_data", entity.getExtraData())
                .fetch().rowsUpdated()
                .then(
                        databaseClient.sql(
                                        "INSERT INTO poliza_estado_actual (id_poliza, estado) " +
                                                "VALUES (:id_poliza, :estado) " +
                                                "ON CONFLICT (id_poliza) " +
                                                "DO UPDATE SET estado = EXCLUDED.estado"
                                )
                                .bind("id_poliza", event.getIdPoliza())
                                .bind("estado", event.getExtraData())
                                .fetch().rowsUpdated()
                )
                .thenReturn(entity);
    }

    public Flux<PolizaEventEntity> getTrazabilidad(String idPoliza) {
        return eventRepo.findAllByIdPoliza(idPoliza);
    }

    public Mono<PolizaEstadoActualEntity> getEstadoActual(String idPoliza) {
        return estadoRepo.findById(idPoliza);
    }
}
