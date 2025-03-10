package com.innovation.routes.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleData {
    private String modelo;
    private int año;
    private Dimensiones dimensiones;
    private String peso;
    private String tipo_grua;
}