package sba.project.tuvanluatgiaothong.dto.response;

import sba.project.tuvanluatgiaothong.enums.UserRole;

import java.time.Instant;

public class RegisterResponse {
    private String fullName;
    private String email;
    private String password;
    private UserRole role;
    private Instant createdAt;
    private boolean isEnable;

    public RegisterResponse(String fullName, String email, String password, UserRole role, Instant createdAt, boolean isEnable) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.isEnable = isEnable;
    }

    public RegisterResponse() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }
}
