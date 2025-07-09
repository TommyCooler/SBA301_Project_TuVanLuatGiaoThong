package sba.project.tuvanluatgiaothong.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sba.project.tuvanluatgiaothong.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FolderNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleException(FolderNotFoundException exception) {
        ApiResponse<Object> response = new ApiResponse<>(
                "not_found",
                exception.getMessage(),
                null
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}