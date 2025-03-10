package com.innovation.routes.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Ubicacion {
    Proveedor proveedor;
    DistanciaTiempo distanciaTiempo;

    public Ubicacion(Proveedor proveedor,DistanciaTiempo distanciaTiempo) {
        this.proveedor= proveedor;
        this.distanciaTiempo= distanciaTiempo;
    }
}
