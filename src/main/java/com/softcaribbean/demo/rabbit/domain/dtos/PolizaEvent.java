package com.softcaribbean.demo.rabbit.domain.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PolizaEvent {
    private String idPoliza;
    private String extraData;
}
