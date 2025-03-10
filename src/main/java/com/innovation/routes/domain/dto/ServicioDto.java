package com.innovation.routes.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicioDto {
    VehicleData vehicleData;
    List<ProveedorDto> providerList;
}
