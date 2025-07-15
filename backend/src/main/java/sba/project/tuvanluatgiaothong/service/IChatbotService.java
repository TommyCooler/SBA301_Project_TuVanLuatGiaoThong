package sba.project.tuvanluatgiaothong.service;

import org.springframework.web.multipart.MultipartFile;
import sba.project.tuvanluatgiaothong.dto.request.ChatRequest;
import sba.project.tuvanluatgiaothong.dto.request.UserPromptRequest;
import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
import sba.project.tuvanluatgiaothong.dto.response.ChatHistoryResponse;
import sba.project.tuvanluatgiaothong.dto.response.ResponseAi;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IChatbotService {

    String generateContent(String prompt);

    ApiResponse<Map<String, String>> deleteChatHistory(UUID chatId);

    ApiResponse<ChatHistoryResponse> renameChatTitle(String chatId, String newTitle);

    ChatHistoryResponse generateWithAuthenticatedUser(UserPromptRequest userPromptRequest);

    List<ChatHistoryResponse> getAllChatHistoriesByUserId(String userId);

    ResponseAi generateContentFromPDF(String url, String prompt);

    ResponseAi generateContentFromPDF(MultipartFile url, String prompt) throws Exception;

    ResponseAi generateContentFromText(String prompt);

    ResponseAi generateContentFromRequest(ChatRequest request) throws Exception;
}
