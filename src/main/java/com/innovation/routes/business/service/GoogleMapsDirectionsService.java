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
