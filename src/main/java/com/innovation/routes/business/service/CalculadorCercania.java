package com.innovation.routes.business.service;

import com.innovation.routes.domain.dto.Ubicacion;
import com.innovation.routes.domain.dto.UbicacionConDistancia;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CalculadorCercania {

    private static final String API_KEY = "AIzaSyDVwi7X_K8prsZwi-X1RX9dboC1pG9a80k";

    public static List<UbicacionConDistancia> encontrarLas3UbicacionesMasCercanas(List<Ubicacion> ubicaciones, double latitudDestino, double longitudDestino) {
        List<UbicacionConDistancia> ubicacionesConDistancia = new ArrayList<>();
        for (Ubicacion ubicacion : ubicaciones) {
            double tiempo = calcularTiempo(ubicacion.getLatitud(), ubicacion.getLongitud(), latitudDestino, longitudDestino);
            ubicacionesConDistancia.add(new UbicacionConDistancia(ubicacion, ubicacion.getDistancia(),tiempo));
        }
        Collections.sort(ubicacionesConDistancia);
        return ubicacionesConDistancia.subList(0, Math.min(3, ubicacionesConDistancia.size()));
    }

    public static double calcularDistancia(double latitud1, double longitud1, double latitud2, double longitud2) {

        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://maps.googleapis.com/maps/api/directions/json").newBuilder();
        urlBuilder.addQueryParameter("origin", latitud1+","+longitud1);
        urlBuilder.addQueryParameter("destination", latitud2+","+longitud2);
        urlBuilder.addQueryParameter("mode","driving");
        urlBuilder.addQueryParameter("departure_time","now");
        urlBuilder.addQueryParameter("key", API_KEY);

        String url = urlBuilder.build().toString();
        System.out.println(url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.err.println("Error en la solicitud: " + response.code());
                System.err.println(response.body().string()); // Imprime el cuerpo de la respuesta de error
                return 0L;
            }

            String responseBody = response.body().string();
            JSONObject json = new JSONObject(responseBody);

            if (json.has("error_message")) {
                System.err.println("Error de la API: " + json.getString("error_message"));
                return 0L;
            }

            JSONArray routes = json.getJSONArray("routes");
            Long distancia= 0L;
            Long duracion = 0L;
            if (routes.length() > 0) {
                JSONObject ruta = routes.getJSONObject(0);
                JSONArray legs = ruta.getJSONArray("legs");

                for (int j= 0;j < legs.length();j++) {
                    JSONObject leg = legs.getJSONObject(j);
                    JSONArray steps = leg.getJSONArray("steps");
                    distancia += leg.getJSONObject("distance").getLong("value");
                    duracion += leg.getJSONObject("duration").getLong("value");
                }
                return convertirDistanciaKm(distancia);
            } else {
                System.out.println("No se encontraron rutas.");
            }
        } catch (JSONException e) {
            System.err.println("Error al procesar JSON: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static double calcularTiempo(double latitud1, double longitud1, double latitud2, double longitud2) {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://maps.googleapis.com/maps/api/directions/json").newBuilder();
        urlBuilder.addQueryParameter("origin", latitud1+","+longitud1);
        urlBuilder.addQueryParameter("destination", latitud2+","+longitud2);
        urlBuilder.addQueryParameter("mode","driving");
        urlBuilder.addQueryParameter("departure_time","now");
        urlBuilder.addQueryParameter("key", API_KEY);

        String url = urlBuilder.build().toString();
        System.out.println(url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.err.println("Error en la solicitud: " + response.code());
                System.err.println(response.body().string()); // Imprime el cuerpo de la respuesta de error
                return 0L;
            }

            String responseBody = response.body().string();
            JSONObject json = new JSONObject(responseBody);

            if (json.has("error_message")) {
                System.err.println("Error de la API: " + json.getString("error_message"));
                return 0L;
            }

            JSONArray routes = json.getJSONArray("routes");
            Long distancia= 0L;
            Long duracion = 0L;
            if (routes.length() > 0) {
                JSONObject ruta = routes.getJSONObject(0);
                JSONArray legs = ruta.getJSONArray("legs");

                for (int j= 0;j < legs.length();j++) {
                    JSONObject leg = legs.getJSONObject(j);
                    JSONArray steps = leg.getJSONArray("steps");
                    distancia += leg.getJSONObject("distance").getLong("value");
                    duracion += leg.getJSONObject("duration").getLong("value");
                }
                return duracion;
            } else {
                System.out.println("No se encontraron rutas.");
            }
        } catch (JSONException e) {
            System.err.println("Error al procesar JSON: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static Float convertirDistanciaKm(long distancia){
        return distancia / 1000f;
    }
}
