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
        double[] coordenadasServicio = GoogleMapsDirectionsService.obtenerCoordenadas(servicio.getUbicacion());
        for (Proveedor proveedor:proveedoresFiltrados) {
            double[] coordenadas = GoogleMapsDirectionsService.obtenerCoordenadas(proveedor.getUbicacionMaps());
            DistanciaTiempo distanciaTiempo = CalculadorCercania.calcularDistanciaTiempo(coordenadas[0],coordenadas[1],coordenadasServicio[0],coordenadasServicio[1]);
            if(distanciaTiempo.getDistancia()<proveedor.getKilometros()){
                ubicaciones.add(new Ubicacion(coordenadas[0],coordenadas[1],proveedor,distanciaTiempo));
            }
        }
        return ubicaciones;
    }
}
