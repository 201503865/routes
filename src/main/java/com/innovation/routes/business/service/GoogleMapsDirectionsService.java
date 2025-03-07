package com.innovation.routes.business.service;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class GoogleMapsDirectionsService {

    private static final String API_KEY = "AIzaSyDVwi7X_K8prsZwi-X1RX9dboC1pG9a80k"; // Reemplaza con tu clave de API

    /*public static void main(String[] args) throws IOException {
        String origin = "14.55435853460435, -90.57749560081751";
        String destination = "14.564933124624108, -90.56200599081501";
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://maps.googleapis.com/maps/api/directions/json").newBuilder();
        urlBuilder.addQueryParameter("origin", origin);
        urlBuilder.addQueryParameter("destination", destination);
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
                return;
            }

            String responseBody = response.body().string();
            JSONObject json = new JSONObject(responseBody);

            if (json.has("error_message")) {
                System.err.println("Error de la API: " + json.getString("error_message"));
                return;
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
                System.out.println("Distancia: "+convertirDistancia(distancia) );
                System.out.println("Duracion: "+convertirTiempo(duracion));
            } else {
                System.out.println("No se encontraron rutas.");
            }
        } catch (JSONException e) {
            System.err.println("Error al procesar JSON: " + e.getMessage());
        }
    }*/

    private static String convertirDistancia(long distancia){
        float distanciaKm = distancia / 1000f;
        return String.format("%.2f km", distanciaKm);
    }
    private static String convertirTiempo(long tiempo){
        long horas = tiempo / 3600;
        long minutos = (tiempo % 3600) / 60;
        return String.format("%d horas %d minutos", horas, minutos);
    }

    public static double[] obtenerCoordenadas(String url) throws Exception {
        String urlCodificada = URLEncoder.encode(url, "UTF-8");
        String urlApi = "https://maps.googleapis.com/maps/api/geocode/json?address=" + urlCodificada + "&key=" + API_KEY;

        URL urlObj = new URL(urlApi);
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
        con.setRequestMethod("GET");

        int respuestaCodigo = con.getResponseCode();
        if (respuestaCodigo == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String lineaEntrada;
            StringBuilder respuesta = new StringBuilder();

            while ((lineaEntrada = in.readLine()) != null) {
                respuesta.append(lineaEntrada);
            }
            in.close();

            JSONObject json = new JSONObject(respuesta.toString());
            if (json.getString("status").equals("OK")) {
                JSONArray resultados = json.getJSONArray("results");
                JSONObject geometria = resultados.getJSONObject(0).getJSONObject("geometry");
                JSONObject ubicacion = geometria.getJSONObject("location");
                double latitud = ubicacion.getDouble("lat");
                double longitud = ubicacion.getDouble("lng");
                return new double[]{latitud, longitud};
            } else {
                System.out.println("Error al obtener coordenadas: " + json.getString("status"));
                return null;
            }
        } else {
            System.out.println("Error en la solicitud HTTP: " + respuestaCodigo);
            return null;
        }
    }
}
