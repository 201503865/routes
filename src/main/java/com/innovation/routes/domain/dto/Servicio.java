package com.innovation.routes.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Servicio {
    private String ubicacion;
    private String servicio;
}
