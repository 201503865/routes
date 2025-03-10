package com.innovation.routes.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DistanciaTiempo {
    double distancia;
    double tiempo;

    public DistanciaTiempo(double distancia, double tiempo){
        this.distancia= distancia;
        this.tiempo = tiempo;
    }
}
