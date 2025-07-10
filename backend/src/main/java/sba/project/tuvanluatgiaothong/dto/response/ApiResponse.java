package sba.project.tuvanluatgiaothong.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {
    private String status;
    private String message;
    private T dataResponse;
}
