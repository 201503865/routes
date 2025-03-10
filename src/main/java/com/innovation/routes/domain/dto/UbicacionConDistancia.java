package com.innovation.routes.domain.dto;

import com.innovation.routes.business.service.CalculadorCercania;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UbicacionConDistancia implements Comparable<UbicacionConDistancia>{
    Ubicacion ubicacion;
    DistanciaTiempo distanciaTiempo;

    public UbicacionConDistancia(Ubicacion ubicacion, DistanciaTiempo distanciaTiempo) {
        this.ubicacion = ubicacion;
        this.distanciaTiempo = distanciaTiempo;
    }

    @Override
    public int compareTo(UbicacionConDistancia otra) {
        return Double.compare(this.distanciaTiempo.getTiempo(), otra.distanciaTiempo.getTiempo());
    }
}
