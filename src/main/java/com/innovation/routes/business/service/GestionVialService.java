package com.innovation.routes.business.service;

import com.innovation.routes.domain.dto.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class GestionVialService {

    public ServicioDto getBetterRoutes(Servicio servicio) throws Exception {
        List<ProveedorDto> proveedorDtos = new ArrayList<>();
        List<Proveedor> proveedors = LectorExcel.leerExcel("Contactos de asistencia.xlsx");
        VehicleData vehicleData = SelectService.chat(servicio.getVehicle());
        if(servicio.getServicio().equals("grua")){
            servicio.setServicio(vehicleData.getTipo_grua());
            if(servicio.getServicio().equals("small")){
                vehicleData.setTipo_grua("Grua pequeña peso maximo 5 toneladas");
            }else if(servicio.getServicio().equals("mediana")){
                vehicleData.setTipo_grua("Grua mediana peso 5-30 toneladas");
            }else {
                vehicleData.setTipo_grua("Grua grande mas de 30 toneladas");
            }
        }
        List<Ubicacion> ubicacions= UbicacionProveedor.ObtenerProveedores(proveedors,servicio);
        List<UbicacionConDistancia> results = CalculadorCercania.encontrarLas3UbicacionesMasCercanas(ubicacions);
        ServicioDto servicioDto = new ServicioDto();
        servicioDto.setVehicleData(vehicleData);
        if(servicio.getServicio().equals("small")||servicio.getServicio().equals("mediana")|| servicio.getServicio().equals("grande")) {
            servicio.setServicio(vehicleData.getTipo_grua());
        }
        for(UbicacionConDistancia result:results){
            ProveedorDto proveedorDto =
                    new ProveedorDto(result.getUbicacion().getProveedor().getNombreProveedor(),
                            convertirTiempo((long)result.getDistanciaTiempo().getTiempo()),
                            new BigDecimal(result.getDistanciaTiempo().getDistancia()).setScale(2, RoundingMode.HALF_UP)+"km",
                            result.getUbicacion().getProveedor().getTelefonos(),
                            result.getUbicacion().getProveedor().getUbicacionMaps(),
                            result.getUbicacion().getProveedor().getDepartamento(),
                            result.getUbicacion().getProveedor().getKilometros() +"km"
                            );
            proveedorDtos.add(proveedorDto);
        }
        servicioDto.setProviderList(proveedorDtos);
        return servicioDto;
    }

    private String convertirTiempo(long tiempo){
        long horas = tiempo / 3600;
        long minutos = (tiempo % 3600) / 60;
        return String.format("%d horas %d minutos", horas, minutos);
    }
}
