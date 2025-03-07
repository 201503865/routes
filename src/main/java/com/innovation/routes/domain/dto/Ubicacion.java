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
    double distancia;

    public Ubicacion(double latitud, double longitud,Proveedor proveedor,double distancia) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.proveedor= proveedor;
        this.distancia= distancia;
    }
}
