package com.innovation.routes.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Proveedor {
    private String departamento;
    private String ubicacion;
    private String ubicacionMaps;
    private String nombreProveedor;
    private String telefonos;
    private double kilometros;
    private boolean pasoCorrienteJumper;
    private boolean pasoCorrienteCables;
    private boolean envioCombustible;
    private boolean cambioLlanta;
    private boolean cerrajeria;
}
