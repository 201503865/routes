package com.innovation.routes.business.service;

import com.innovation.routes.domain.dto.Proveedor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServicioProveedor {
    private static final HashMap<String, String> SERVICIO_A_PROPIEDAD = new HashMap<>();

    static {
        SERVICIO_A_PROPIEDAD.put("pasoCorrienteJumper", "pasoCorrienteJumper");
        SERVICIO_A_PROPIEDAD.put("pasoCorrienteCables", "pasoCorrienteCables");
        SERVICIO_A_PROPIEDAD.put("envioCombustible", "envioCombustible");
        SERVICIO_A_PROPIEDAD.put("cerrajeria", "cerrajeria");

    }

    public static List<Proveedor> filtrarProveedoresPorServicio(List<Proveedor> proveedores, String servicio) {
        String propiedad = SERVICIO_A_PROPIEDAD.get(servicio);
        if (propiedad == null) {
            return new ArrayList<>();
        }

        List<Proveedor> proveedoresFiltrados = new ArrayList<>();
        for (Proveedor proveedor : proveedores) {
            try {
                java.lang.reflect.Field field = Proveedor.class.getDeclaredField(propiedad);
                field.setAccessible(true);
                boolean valor = field.getBoolean(proveedor);
                if (valor) {
                    proveedoresFiltrados.add(proveedor);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return proveedoresFiltrados;
    }
}
