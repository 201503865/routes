package com.innovation.routes.business.service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.LatLng;
import com.google.ortools.constraintsolver.RoutingIndexManager;
import com.google.ortools.constraintsolver.RoutingModel;
import com.google.ortools.constraintsolver.RoutingDimension;
import com.innovation.routes.domain.dto.UbicacionConDistancia;

import java.util.List;

public class OptimizadorRutasAntiguo {
    public static void optimizarRutasAntiguo(RoutingIndexManager manager, List<UbicacionConDistancia> ubicacionesMasCercanas, LatLng destino, GeoApiContext context) {
        /*RoutingModel routing = new RoutingModel(manager);

        // Crear una dimensión para las distancias
        RoutingDimension distanceDimension = routing.getOrCreateDimension("Distance", 0, Integer.MAX_VALUE, true);

        // Definir la función de costo
        for (int i = 0; i < manager.getNumberOfNodes(); i++) {
            for (int j = 0; j < manager.getNumberOfNodes(); j++) {
                if (i != j) {
                    long fromNode = manager.indexToNode(i);
                    long toNode = manager.indexToNode(j);

                    LatLng origen;
                    LatLng destino2;

                    if (fromNode == 0) {
                        origen = destino;
                    } else {
                        origen = new LatLng(ubicacionesMasCercanas.get((int) (fromNode - 1)).ubicacion.latitud, ubicacionesMasCercanas.get((int) (fromNode - 1)).ubicacion.longitud);
                    }

                    if (toNode == 0) {
                        destino2 = destino;
                    } else {
                        destino2 = new LatLng(ubicacionesMasCercanas.get((int) (toNode - 1)).ubicacion.latitud, ubicacionesMasCercanas.get((int) (toNode - 1)).ubicacion.longitud);
                    }

                    try {
                        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context)
                                .origins(origen)
                                .destinations(destino2);
                        DistanceMatrix result = req.await();
                        DistanceMatrixRow row = result.rows[0];
                        DistanceMatrixElement element = row.elements[0];
                        int distance = (int) element.distance.inMeters; // Distancia en metros (int)
                        distanceDimension.setArcCost(i, j, distance);
                    } catch (Exception e) {
                        e.printStackTrace();
                        distanceDimension.setArcCost(i, j, 0); // Manejo de errores
                    }
                }
            }
        }

        // ... (resto de tu código para configurar la búsqueda y obtener la solución)*/
    }
}
