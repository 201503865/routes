package com.innovation.routes.business.service;

import com.innovation.routes.domain.dto.DistanciaTiempo;
import com.innovation.routes.domain.dto.Proveedor;
import com.innovation.routes.domain.dto.Servicio;
import com.innovation.routes.domain.dto.Ubicacion;

import java.util.ArrayList;
import java.util.List;

public class UbicacionProveedor {

    public static List<Ubicacion> ObtenerProveedores(List<Proveedor> proveedores, Servicio servicio) throws Exception {
        List<Ubicacion> ubicaciones = new ArrayList<>();
        List<Proveedor> proveedoresFiltrados = ServicioProveedor.filtrarProveedoresPorServicio(proveedores, servicio.getServicio());
        for (Proveedor proveedor:proveedoresFiltrados) {
            DistanciaTiempo distanciaTiempo = CalculadorCercania.calcularDistanciaTiempo(proveedor.getLatitud(),
                    proveedor.getLongitud(),
                    servicio.getLatitud(),
                    servicio.getLongitud());
            if(distanciaTiempo.getDistancia()<proveedor.getKilometros()){
                ubicaciones.add(new Ubicacion(proveedor,distanciaTiempo));
            }
        }
        return ubicaciones;
    }
}
