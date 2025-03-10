package com.innovation.routes.business.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innovation.routes.domain.dto.Servicio;
import com.innovation.routes.domain.dto.VehicleData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class SelectService {

    private static final Logger logger = LoggerFactory.getLogger(SelectService.class);

    private static final String GEMINI_MODEL = "gemini-2.0-flash";

    private static final String API_KEY ="AIzaSyDphHvIcn8ywzXXBZoANE845Uv_zGwrUZg";
    public static VehicleData chat(String vehicle) {

        String fullPrompt = "Podrias darme un json con la siguiente estructura\n" +
                "\n" +
                "{\n" +
                "\n" +
                "\"modelo\": \"\",\n" +
                "\n" +
                "\"año\": ,\n" +
                "\n" +
                "\"dimensiones\": {\n" +
                "\n" +
                "\"longitud\": \"\",\n" +
                "\n" +
                "\"ancho\": \"\",\n" +
                "\n" +
                "\"altura\": \"\",\n" +
                "\n" +
                "\"distancia_entre_ejes\": \"\"\n" +
                "\n" +
                "},\n" +
                "\n" +
                "\"peso\": \"\",\n" +
                "\n" +
                "\"tipo_grua\": \"\"\n" +
                "\n" +
                "}\n" +
                "\n" +
                "Para un vehiculo"+ vehicle +"en tipo de grua es cual necesito para moverlo tengo small 5 toneladas mediana de 5-30 y grande mayor de 30, en tipo de grua solo necesito que coloques solo una de las palabras: small, mediana o grande y el peso en kg y las dimensiones en mentros";

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Prepare request entity
        fullPrompt = getPromptBody(fullPrompt);
        HttpEntity<String> requestEntity = new HttpEntity<>(fullPrompt, headers);


        // Perform HTTP POST request
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "https://generativelanguage.googleapis.com/v1beta/models/" + GEMINI_MODEL + ":generateContent?key="+ API_KEY,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        HttpStatus statusCode = (HttpStatus) responseEntity.getStatusCode();
        if (statusCode == HttpStatus.OK) {
            String responseText = responseEntity.getBody();
            try {
                responseText = parseGeminiResponse(responseText);
            } catch (Exception e) {
                logger.error("Error in Parding");
            }
            return convertDataToVehicle(responseText); // Return the fetched summary response
        } else {
            throw new RuntimeException("API request failed with status code: " + statusCode + " and response: " + responseEntity.getBody());
        }
    }

    private static VehicleData convertDataToVehicle(String texto) {
        int inicio = texto.indexOf('{');
        int fin = texto.lastIndexOf('}') + 1;
        String jsonStr = texto.substring(inicio, fin);

        // 2. Convertir el JSON a un objeto JsonNode
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(jsonStr);
            VehicleData vehicleData = objectMapper.treeToValue(jsonNode, VehicleData.class);
            return vehicleData;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // 3. Mapear el JsonNode a un objeto Java (opcional)

        return  null;
    }

    public static String getPromptBody(String prompt) {
        JSONObject promptJson = new JSONObject();
        JSONArray contentsArray = new JSONArray();
        JSONObject contentsObject = new JSONObject();
        contentsObject.put("role", "user");
        JSONArray partsArray = new JSONArray();
        JSONObject partsObject = new JSONObject();
        partsObject.put("text", prompt);
        partsArray.add(partsObject);
        contentsObject.put("parts", partsArray);
        contentsArray.add(contentsObject);
        promptJson.put("contents", contentsArray);
        JSONObject parametersJson = new JSONObject();
        parametersJson.put("temperature", 0.5);
        parametersJson.put("topP", 0.99);
        promptJson.put("generationConfig", parametersJson);
        return promptJson.toJSONString();
    }

    public static String parseGeminiResponse(String jsonResponse) throws IOException, ParseException {
        // Parse the JSON string
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(jsonResponse);

        // Get the "candidates" array
        JSONArray candidatesArray = (JSONArray) jsonObject.get("candidates");

        // Assuming there's only one candidate (index 0), extract its content
        JSONObject candidateObject = (JSONObject) candidatesArray.get(0);
        JSONObject contentObject = (JSONObject) candidateObject.get("content");

        // Get the "parts" array within the content
        JSONArray partsArray = (JSONArray) contentObject.get("parts");

        // Assuming there's only one part (index 0), extract its text
        JSONObject partObject = (JSONObject) partsArray.get(0);
        String responseText = (String) partObject.get("text");

        return responseText;
    }

    public static String serviceType(Servicio servicio){
        if(servicio.getServicio().equals("grua")){
            VehicleData vehicleData = chat(servicio.getVehicle());
            return vehicleData.getTipo_grua();
        }
        return servicio.getServicio();
    }
}
