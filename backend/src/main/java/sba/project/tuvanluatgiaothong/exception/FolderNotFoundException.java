package sba.project.tuvanluatgiaothong.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FolderNotFoundException extends RuntimeException {

    @JsonProperty("message")
    private final String message;

    public FolderNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
