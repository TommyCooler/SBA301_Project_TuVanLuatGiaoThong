package sba.project.tuvanluatgiaothong.service;

import org.springframework.web.multipart.MultipartFile;
import sba.project.tuvanluatgiaothong.dto.request.UserPromptRequest;
import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
import sba.project.tuvanluatgiaothong.dto.response.ChatHistoryResponse;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IChatbotService {

    ApiResponse<Map<String, String>> deleteChatHistory(UUID chatId);

    ApiResponse<ChatHistoryResponse> renameChatTitle(String chatId, String newTitle);

    ChatHistoryResponse generateWithAuthenticatedUser(UserPromptRequest userPromptRequest);

    List<ChatHistoryResponse> getAllChatHistoriesByUserId(String userId);


}
