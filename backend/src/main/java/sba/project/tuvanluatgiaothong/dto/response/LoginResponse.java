package sba.project.tuvanluatgiaothong.dto.response;

public class LoginResponse {
    private String email;
    private String role;

    public LoginResponse() {
    }

    public LoginResponse(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
