package sba.project.tuvanluatgiaothong.dto.response;

import sba.project.tuvanluatgiaothong.pojo.User;

public class LoginResponse {
    private String email;
    private User.Role role;

    public LoginResponse() {
    }

    public LoginResponse(String email, User.Role role) {
        this.email = email;
        this.role = role;
    }

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
