package com.innovation.routes.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public  class Dimensiones {
    private String longitud;
    private String ancho;
    private String altura;
    private String distancia_entre_ejes;
}
