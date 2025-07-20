package sba.project.tuvanluatgiaothong.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sba.project.tuvanluatgiaothong.dto.request.UserPromptRequest;
import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
import sba.project.tuvanluatgiaothong.service.IChatbotService;
import sba.project.tuvanluatgiaothong.service.ITrackingLimitationService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chatbot")
public class ChatbotController {

    private final IChatbotService iChatbotService;

    private final ITrackingLimitationService iTrackingLimitationService;

    private final RedisTemplate<String, String> redisTemplate;

    @GetMapping("/health")
    public String healthCheck() {
        return "Chatbot service is running";
    }

    @PutMapping("/rename-title")
    public ResponseEntity<ApiResponse<?>> renameChatTitle(
            @RequestParam("chatId") String chatId,
            @RequestParam("newTitle") String newTitle) {
        return new ResponseEntity<>(this.iChatbotService.renameChatTitle(chatId, newTitle), HttpStatus.OK);
    }

    @DeleteMapping("/delete-history/{chatId}")
    public ResponseEntity<ApiResponse<?>> deleteChatHistory(@PathVariable("chatId") UUID chatId) {
        return new ResponseEntity<>(this.iChatbotService.deleteChatHistory(chatId), HttpStatus.OK);
    }

    @PostMapping("/authenticated-user/generate")
    public ResponseEntity<ApiResponse<?>> generateWithAuthenticatedUser(
            @RequestBody UserPromptRequest userPromptRequest) {
        if (this.iTrackingLimitationService.canUserAsk(
                userPromptRequest.getUserId())) {
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .status("success")
                            .message("Content generated successfully for authenticated user")
                            .dataResponse(iChatbotService.generateWithAuthenticatedUser(userPromptRequest))
                            .build());
        } else {
            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .status("fail")
                            .message("Daily limit reached for user")
                            .dataResponse(null)
                            .build());
        }
    }

    @GetMapping("/authenticated-user/get-histories/{userId}")
    public ResponseEntity<ApiResponse<?>> getAllChatHistoriesByUserId(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .status("success")
                        .message("Content generated successfully for authenticated user")
                        .dataResponse(iChatbotService.getAllChatHistoriesByUserId(userId))
                        .build());
    }

    @GetMapping("/test-redis")
    public ResponseEntity<Map<String, String>> health() {
        String status;
        try {
            redisTemplate.opsForValue().set("test:ping", "pong", 10, TimeUnit.SECONDS);
            String result = redisTemplate.opsForValue().get("test:ping");
            status = "Redis connection successful: " + result;
        } catch (Exception e) {
            status = "Redis connection failed: " + e.getMessage();
        }
        Map<String, String> response = new HashMap<>();
        response.put("status", status);
        return ResponseEntity.ok(response);
    }

}
