package sba.project.tuvanluatgiaothong.dto.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private String status;
    private String message;
    private T dataResponse;

    public ApiResponse(T dataResponse, String message, String status) {
        this.dataResponse = dataResponse;
        this.message = message;
        this.status = status;
    }
}

