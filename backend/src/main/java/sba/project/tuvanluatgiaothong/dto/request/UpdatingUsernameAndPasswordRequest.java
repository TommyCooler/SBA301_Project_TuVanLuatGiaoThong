package sba.project.tuvanluatgiaothong.dto.request;

public class UpdatingUsernameAndPasswordRequest {
    private String newUsername;
    private String newPassword;

    public UpdatingUsernameAndPasswordRequest() {}

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
