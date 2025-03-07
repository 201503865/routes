package com.innovation.routes.business.service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.LatLng;
import com.google.ortools.constraintsolver.*;
import com.innovation.routes.domain.dto.UbicacionConDistancia;

import java.util.List;

public class OptimizadorRutasGoogleMaps {
    public static void optimizarRutasConGoogleMaps(List<UbicacionConDistancia> ubicacionesMasCercanas, double latitudDestino, double longitudDestino, String apiKey) throws Exception {

 /*       GeoApiContext context = new GeoApiContext.Builder().apiKey(apiKey).build();
        LatLng destino = new LatLng(latitudDestino, longitudDestino);

        int numUbicaciones = ubicacionesMasCercanas.size();
        RoutingIndexManager manager = new RoutingIndexManager(numUbicaciones + 1, 1, 0);
        RoutingModel routing = new RoutingModel(manager);
        // Función de costo usando la API de Google Maps
        routing.setArcCostEvaluatorOfAllVehicles(index -> {
            int fromIndex = manager.indexToNode(Long.valueOf(index));
            int toIndex = manager.indexToNode(index);
            if (fromIndex == toIndex) {
                return 0;
            }
            LatLng origen;
            LatLng destino2;

            if (fromIndex == 0) {
                origen = destino;
            } else {
                origen = new LatLng(ubicacionesMasCercanas.get(fromIndex - 1).ubicacion.latitud, ubicacionesMasCercanas.get(fromIndex - 1).ubicacion.longitud);
            }

            if (toIndex == 0) {
                destino2 = destino;
            } else {
                destino2 = new LatLng(ubicacionesMasCercanas.get(toIndex - 1).ubicacion.latitud, ubicacionesMasCercanas.get(toIndex - 1).ubicacion.longitud);
            }

            try {
                DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context)
                        .origins(origen)
                        .destinations(destino2);
                DistanceMatrix result = req.await();
                DistanceMatrixRow row = result.rows[0];
                DistanceMatrixElement element = row.elements[0];
                return (int) element.distance.inMeters; // Distancia en metros
            } catch (Exception e) {
                e.printStackTrace();
                return 0; // Manejo de errores
            }
        });

        // Configuración de la búsqueda
        RoutingSearchParameters searchParameters = main.defaultRoutingSearchParameters()
                .toBuilder()
                .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PATH_CHEAPEST_ARC)
                .build();

        // Solución
        com.google.ortools.constraintsolver.Assignment solution = routing.solveWithParameters(searchParameters);

        // Imprimir la ruta óptima
        if (solution != null) {
            // ... (código para imprimir la ruta óptima)
        } else {
            System.out.println("No se encontró solución.");
        }*/
    }
}
