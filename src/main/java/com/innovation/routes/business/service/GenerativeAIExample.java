package com.innovation.routes.business.service;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.aiplatform.v1.EndpointName;
import com.google.cloud.aiplatform.v1.PredictResponse;
import com.google.cloud.aiplatform.v1.PredictionServiceClient;
import com.google.cloud.aiplatform.v1.PredictionServiceSettings;
import com.google.protobuf.ListValue;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GenerativeAIExample {
    public static void main(String[] args) throws IOException {
        String projectId = "gen-lang-client-0023630861";
        String location = "us-central1";
        String model = "gemini-2.0-flash-001";
        String apiUrl = String.format("https://%s-aiplatform.googleapis.com/v1/projects/%s/locations/%s/publishers/google/models/%s:streamGenerateContent",
                location, projectId, location, model);

        String accessToken = getAccessToken(); // Implementa la obtención del token de acceso

        OkHttpClient client = new OkHttpClient();

        JSONObject requestBody = new JSONObject();
        JSONObject contents = new JSONObject();
        contents.put("role", "user");
        JSONArray parts = new JSONArray();
        JSONObject part = new JSONObject();
        part.put("text", "Necesito la informacion de un carro Toyota Matrix 2009 S me podrias dar los siguientes datos en un json: peso y dimensiones adicional si tengo esta clasificacion grua pequeña 5 tonelados, grua mediana 5-30 toneladas y grua grande mas de 30 toneladas podrias agregarle a ese json que tipo de grua necesito");
        parts.put(part);
        contents.put("parts", parts);
        JSONArray contentsArray = new JSONArray();
        contentsArray.put(contents);
        requestBody.put("contents", contentsArray);

        JSONObject generationConfig = new JSONObject();
        generationConfig.put("temperature", 1.0);
        generationConfig.put("topP", 0.95);
        generationConfig.put("maxOutputTokens", 8192);
        JSONArray responseModalities = new JSONArray();
        responseModalities.put("TEXT");
        generationConfig.put("responseModalities", responseModalities);
        requestBody.put("generationConfig", generationConfig);

        RequestBody body = RequestBody.create(requestBody.toString(), MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(apiUrl)
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String responseString = responseBody.string();
                // Process the streamed response here. You'll need to parse the JSON stream.
                System.out.println(responseString);
            }
        }
    }

    private static String getAccessToken() throws IOException {

        String credentialsResourcePath = "ia_client.json";

        ClassPathResource resource = new ClassPathResource(credentialsResourcePath);
        InputStream inputStream = resource.getInputStream();

        GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);
        AccessToken accessToken = credentials.getAccessToken();


        System.out.println("Access Token: " + accessToken.getTokenValue());
        return accessToken.getTokenValue();
    }
}
