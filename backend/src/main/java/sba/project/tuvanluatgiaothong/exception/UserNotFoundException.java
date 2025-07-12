package sba.project.tuvanluatgiaothong.exception;

public class UserNotFoundException extends RuntimeException {

    private final String errorCode;

    public UserNotFoundException() {
        super("User not found");
        this.errorCode = "USER_NOT_FOUND";
    }

    public UserNotFoundException(String message) {
        super(message);
        this.errorCode = "USER_NOT_FOUND";
    }

    public UserNotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
