package sba.project.tuvanluatgiaothong.dto.response;

import sba.project.tuvanluatgiaothong.enums.UserRole;

public class LoginResponse {
    private String email;
    private UserRole role;
    private String token;
    private String fullName;

    public LoginResponse() {
    }

    public LoginResponse(String email, UserRole role, String token, String fullName) {
        this.email = email;
        this.role = role;
        this.token = token;
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
