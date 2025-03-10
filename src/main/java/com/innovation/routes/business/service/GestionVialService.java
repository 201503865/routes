package com.innovation.routes.business.service;

import com.innovation.routes.domain.dto.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class GestionVialService {

    public List<ProveedorDto> getBetterRoutes(Servicio servicio) throws Exception {
        List<ProveedorDto> proveedorDtos = new ArrayList<>();
        List<Proveedor> proveedors = LectorExcel.leerExcel("Contactos de asistencia.xlsx");
        String serviceType = SelectService.serviceType(servicio);
        servicio.setServicio(serviceType);
        List<Ubicacion> ubicacions= UbicacionProveedor.ObtenerProveedores(proveedors,servicio);
        double[] coordenadasServicio = GoogleMapsDirectionsService.obtenerCoordenadas(servicio.getUbicacion());
        List<UbicacionConDistancia> results = CalculadorCercania.encontrarLas3UbicacionesMasCercanas(ubicacions,coordenadasServicio[0],coordenadasServicio[1]);
        for(UbicacionConDistancia result:results){
            System.out.println("Proveedor: "+result.getUbicacion().getProveedor().getNombreProveedor());
            System.out.println("Tiempo: "+convertirTiempo((long)result.getDistanciaTiempo().getTiempo()));
            System.out.println("Distancia: "+ new BigDecimal(result.getDistanciaTiempo().getDistancia()).setScale(2, RoundingMode.HALF_UP)+"km");
            ProveedorDto proveedorDto =new ProveedorDto(result.getUbicacion().getProveedor().getNombreProveedor(),convertirTiempo((long)result.getDistanciaTiempo().getTiempo()),new BigDecimal(result.getDistanciaTiempo().getDistancia()).setScale(2, RoundingMode.HALF_UP)+"km",serviceType);
            proveedorDtos.add(proveedorDto);
        }
        return proveedorDtos;
    }

    private String convertirTiempo(long tiempo){
        long horas = tiempo / 3600;
        long minutos = (tiempo % 3600) / 60;
        return String.format("%d horas %d minutos", horas, minutos);
    }
}
