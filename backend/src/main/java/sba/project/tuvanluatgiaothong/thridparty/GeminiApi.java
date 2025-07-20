package sba.project.tuvanluatgiaothong.thridparty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import sba.project.tuvanluatgiaothong.config.ChatbotConfiguration;
import sba.project.tuvanluatgiaothong.dto.response.GeminiResponse;
import sba.project.tuvanluatgiaothong.dto.response.GeminiTrafficResponse;
import sba.project.tuvanluatgiaothong.dto.response.TrafficLawResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class GeminiApi implements IGeminiApi {

    private final ChatbotConfiguration config;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final static String ENDPOINT_TEMPLATE = "https://generativelanguage.googleapis.com/v1beta/models/%s:generateContent?key=";

//    public GeminiResponse generateContentAsObject(String prompt, List<String> contexts, String geminiAlias) {
//        String fullPrompt = this.injectContextToPrompt(contexts, prompt);
//        try {
//            RestTemplate restTemplate = new RestTemplate();
//            String endpoint = String.format(ENDPOINT_TEMPLATE, geminiAlias) + config.getApiKey();
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            Map<String, Object> content = Map.of("parts", List.of(Map.of("text", fullPrompt)));
//            Map<String, Object> body = Map.of("contents", List.of(content));
//            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
//            ResponseEntity<GeminiResponse> response = restTemplate.postForEntity(
//                    endpoint, request, GeminiResponse.class);
//            return response.getBody();
//
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    @Override
    public GeminiResponse generateContentAsObject(String prompt, List<String> contexts, String geminiAlias) {
        String fullPrompt = this.injectContextToPrompt(contexts, prompt);
        try {
            RestTemplate restTemplate = new RestTemplate();
            String endpoint = String.format(ENDPOINT_TEMPLATE, geminiAlias) + config.getApiKey();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> content = Map.of("parts", List.of(Map.of("text", fullPrompt)));
            Map<String, Object> tool = Map.of("google_search", Map.of());

            Map<String, Object> body = Map.of(
                    "contents", List.of(content),
                    "tools", List.of(tool)
            );

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
            ResponseEntity<GeminiResponse> response = restTemplate.postForEntity(
                    endpoint, request, GeminiResponse.class);
            return response.getBody();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Response with structured response json
    @Override
    public GeminiTrafficResponse generateTrafficLawResponse(String prompt, List<String> contexts, String geminiAlias) {
        try {
            GeminiResponse rawResponse = generateContentAsObject(prompt, contexts, geminiAlias);
            if (rawResponse == null) {
                GeminiTrafficResponse errorResponse = new GeminiTrafficResponse();
                errorResponse.setErrorMessage("Failed to get response from Gemini API");
                return errorResponse;
            }
            // Extract text content
            String textContent = rawResponse.getTextContent();

            if (textContent == null || textContent.trim().isEmpty()) {
                GeminiTrafficResponse errorResponse = new GeminiTrafficResponse();
                errorResponse.setRawGeminiResponse(rawResponse);
                errorResponse.setErrorMessage("Empty response from Gemini API");
                return errorResponse;
            }
            TrafficLawResponse trafficLawData = parseJsonFromText(textContent);
            GeminiTrafficResponse result = new GeminiTrafficResponse(trafficLawData, rawResponse);
            if (trafficLawData == null) {
                result.setErrorMessage("Failed to parse JSON from response: " + textContent);
            }
            return result;
        }
        catch (Exception e) {
            GeminiTrafficResponse errorResponse = new GeminiTrafficResponse();
            errorResponse.setErrorMessage("Exception occurred: " + e.getMessage());
            return errorResponse;
        }
    }

    private TrafficLawResponse parseJsonFromText(String textContent) {
        try {
            return objectMapper.readValue(textContent.trim(), TrafficLawResponse.class);
        }
        catch (JsonProcessingException e) {
            return extractJsonFromText(textContent);
        }
    }


    private TrafficLawResponse extractJsonFromText(String textContent) {
        try {
            Pattern jsonPattern = Pattern.compile("\\{[^{}]*\"summarize_answer\"[^{}]*\"full_answer\"[^{}]*\\}",
                    Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
            Matcher matcher = jsonPattern.matcher(textContent);

            if (matcher.find()) {
                String jsonBlock = matcher.group();
                return objectMapper.readValue(jsonBlock, TrafficLawResponse.class);
            }

            Pattern alternativePattern = Pattern.compile("\\{.*?\\}", Pattern.DOTALL);
            Matcher altMatcher = alternativePattern.matcher(textContent);

            while (altMatcher.find()) {
                String potentialJson = altMatcher.group();
                try {
                    TrafficLawResponse response = objectMapper.readValue(potentialJson, TrafficLawResponse.class);
                    if (response.getSummarizeAnswer() != null && response.getFullAnswer() != null) {
                        return response;
                    }
                }
                catch (JsonProcessingException ignored) {
                    // Continue searching
                }
            }

            return null;
        }
        catch (Exception e) {
            return null;
        }
    }

    private String injectContextToPrompt(
            List<String> contexts,
            String userQuestion
    ) {
        if (contexts == null || contexts.isEmpty()) {
            return String.format(ENGLISH_INSTRUCTION_PROMPT, userQuestion);
        }
        String contextBlock = String.join("\n", contexts);
        String promptWithContext = ENGLISH_INSTRUCTION_PROMPT.replace("(No context history)", contextBlock);
        return String.format(promptWithContext, userQuestion);
    }

    private static final String ENGLISH_INSTRUCTION_PROMPT = """
            <|BEGIN_INSTRUCTION_PROMPT|> \s
            You are an AI assistant specialized in advising on Vietnamese traffic laws. \s
            - Only answer questions related to traffic regulations, road signs, penalties, and traffic administrative procedures in Vietnam. \s
            - Do not answer questions outside this scope. \s
            - Do not provide personal opinions or advice that is not based on official laws. \s
            - You can search information on the internet to collect newest data
            - If unsure, suggest the user consult official government sources or legal professionals. \s
            - All information provided is for reference only and does not replace advice from a qualified lawyer or competent authority.\s
            - Answer with Vietnamese language\s
            Please answer the user's question in two formats: \s
                - "summarize_answer": A short summary (no more than 2 sentences) that captures the main idea. \s
                - "full_answer": A detailed and comprehensive answer with clear explanations.\s
                Return your answer as a single valid JSON object with the fields "summarize_answer" and "full_answer". Do not include anything else outside the JSON block.
                Example format:\s
                {
                  "summarize_answer": "...",
                  "full_answer": "..."
                }\s
            <|END_INSTRUCTION_PROMPT|>\s
            <|BEGIN_CHAT_CONTEXT_HISTORY|>
                (No context history) 
            <|END_CHAT_CONTEXT_HISTORY|>\s
            <|BEGIN_OF_USER_CONTENT|>
            %s
            <|END_OF_USER_CONTENT|>
            """;
}