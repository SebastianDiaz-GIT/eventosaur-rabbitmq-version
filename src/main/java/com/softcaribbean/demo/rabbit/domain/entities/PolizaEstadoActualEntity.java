package com.softcaribbean.demo.rabbit.domain.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("poliza_estado_actual")
public class PolizaEstadoActualEntity {
    @Id
    @Column("id_poliza")
    private String idPoliza;
    @Column("estado")
    private String estado;

    public String getIdPoliza() {
        return idPoliza;
    }

    public void setIdPoliza(String idPoliza) {
        this.idPoliza = idPoliza;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
