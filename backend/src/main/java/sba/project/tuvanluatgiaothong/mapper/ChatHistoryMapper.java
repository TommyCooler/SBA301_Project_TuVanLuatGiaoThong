package sba.project.tuvanluatgiaothong.mapper;

import lombok.RequiredArgsConstructor;
import sba.project.tuvanluatgiaothong.dto.response.ChatHistoryResponse;
import sba.project.tuvanluatgiaothong.dto.response.ChatItemResponse;
import sba.project.tuvanluatgiaothong.pojo.ChatHistory;
import sba.project.tuvanluatgiaothong.pojo.ChatItem;

import org.springframework.stereotype.Component;
import java.time.Instant;

@Component("chatHistoryMapper_ChatbotService")
@RequiredArgsConstructor
public class ChatHistoryMapper {

    public ChatHistoryResponse toResponse(ChatHistory chatHistory) {
        return ChatHistoryResponse.builder()
                .id(chatHistory.getId())
                .chatTitle(chatHistory.getChatTitle())
                .histories(chatHistory.getHistories().stream().map(
                        this::toChatItemResponse
                ).toList())
                .createdDate(chatHistory.getCreatedDate() != null ? Instant.parse(chatHistory.getCreatedDate()) : null)
                .build();
    }

    private ChatItemResponse toChatItemResponse(ChatItem chatItem) {
        return ChatItemResponse.builder()
                .userText(chatItem.getUserText())
                .botText(chatItem.getBotText())
                .createdDate(chatItem.getCreatedDate() != null ? Instant.parse(chatItem.getCreatedDate()) : null)
                .build();
    }

}
