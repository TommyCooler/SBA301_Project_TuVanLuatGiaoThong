package sba.project.tuvanluatgiaothong.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class UserPromptRequest {

    UUID chatId;

    String userId;

    String prompt;

}
