package sba.project.tuvanluatgiaothong.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import sba.project.tuvanluatgiaothong.config.GeminiConfig;

@Service
public class GeminiService implements IGeminiService {
    
    private final GeminiConfig config;
    private final RestTemplate restTemplate;

    public GeminiService(GeminiConfig config) {
        this.config = config;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public String generateContent(String prompt) {
        String endpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + config.getApiKey();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Construct request body
        Map<String, Object> content = Map.of("parts", List.of(Map.of("text", prompt)));
        Map<String, Object> body = Map.of("contents", List.of(content));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(endpoint, request, String.class);

        return response.getBody();

    }

}
