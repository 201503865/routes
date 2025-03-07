package com.innovation.routes.domain.dto;

import com.innovation.routes.business.service.CalculadorCercania;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UbicacionConDistancia implements Comparable<UbicacionConDistancia>{
    Ubicacion ubicacion;
    double distancia;
    double tiempo;

    public UbicacionConDistancia(Ubicacion ubicacion, double distancia,double tiempo) {
        this.ubicacion = ubicacion;
        this.distancia = distancia;
        this.tiempo = tiempo;
    }

    @Override
    public int compareTo(UbicacionConDistancia otra) {
        return Double.compare(this.distancia, otra.distancia);
    }
}
