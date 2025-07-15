package sba.project.tuvanluatgiaothong.dto.response;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class NenResponse {
    UUID id;
    Instant createdDate;
    String chatTitle;
    List<ChatItemResponse> histories;
    String sessionId;
    String action;
}
