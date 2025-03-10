package com.innovation.routes.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Ubicacion {
    double latitud;
    double longitud;
    Proveedor proveedor;
    DistanciaTiempo distanciaTiempo;

    public Ubicacion(double latitud, double longitud,Proveedor proveedor,DistanciaTiempo distanciaTiempo) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.proveedor= proveedor;
        this.distanciaTiempo= distanciaTiempo;
    }
}
