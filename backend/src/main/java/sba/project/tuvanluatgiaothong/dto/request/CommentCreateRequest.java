package sba.project.tuvanluatgiaothong.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class CommentCreateRequest {
    String username;
    String fullname;
    String avatarUrl;
    Boolean isAnonymous;

    @NotBlank
    String content;

    @Min(1)
    @Max(5)
    int rating;
}
