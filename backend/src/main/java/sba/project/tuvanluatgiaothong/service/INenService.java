package sba.project.tuvanluatgiaothong.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import sba.project.tuvanluatgiaothong.dto.request.NenRequest;
import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
import sba.project.tuvanluatgiaothong.dto.response.ChatHistoryResponse;

public interface INenService {
    ChatHistoryResponse generateWithAuthenticatedUser(NenRequest request);
    ApiResponse<Map<String, String>> deleteChatHistory(UUID chatId);
    ApiResponse<ChatHistoryResponse> renameChatTitle(String chatId, String newTitle);
    List<ChatHistoryResponse> getAllChatHistoriesByUserId(String userId);
}
