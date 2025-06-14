package sba.project.tuvanluatgiaothong.dto.response;

import sba.project.tuvanluatgiaothong.enums.UserRole;
import sba.project.tuvanluatgiaothong.pojo.User;

public class LoginResponse {
    private String email;
    private UserRole role;

    public LoginResponse() {
    }

    public LoginResponse(String email, UserRole role) {
        this.email = email;
        this.role = role;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
