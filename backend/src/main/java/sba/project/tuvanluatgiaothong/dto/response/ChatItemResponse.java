package sba.project.tuvanluatgiaothong.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ChatItemResponse {

    private String userText;

    private String botText;

    private Instant createdDate;

}
