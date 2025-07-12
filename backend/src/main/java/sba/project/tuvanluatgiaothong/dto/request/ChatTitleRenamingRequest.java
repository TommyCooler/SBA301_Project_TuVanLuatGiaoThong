package sba.project.tuvanluatgiaothong.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class ChatTitleRenamingRequest {

    UUID chatId;

    String newTitle;

}
