package sba.project.tuvanluatgiaothong.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

import java.util.List;
import java.util.Map;

import sba.project.tuvanluatgiaothong.config.GeminiConfig;

@Service
public class GeminiService {
    
    private final GeminiConfig config;
    private final RestTemplate restTemplate;

    public GeminiService(GeminiConfig config) {
        this.config = config;
        this.restTemplate = new RestTemplate();
    }

    public String generateContent(String prompt) {
        String endpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + config.getApiKey();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Construct request body
        Map<String, Object> content = Map.of("parts", List.of(Map.of("text", prompt)));
        Map<String, Object> body = Map.of("contents", List.of(content));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(endpoint, request, String.class);


        Client client = new Client();

        GenerateContentResponse response1 =
            client.models.generateContent(
                "gemini-2.0-flash",
                "Explain how AI works in a few words",
                null);

        System.out.println("Client response: " + response1.text());
        client.close();

        return response.getBody();

    }

}
