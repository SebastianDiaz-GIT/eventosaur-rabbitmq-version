package com.softcaribbean.demo.rabbit.domain.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.util.UUID;

@Table("poliza_evento")
@Data
public class PolizaEventEntity {
    @Id
    @Column("id")
    private String id;
    @Column("id_poliza")
    private String idPoliza;
    @Column("extra_data")
    private String extraData;
}
