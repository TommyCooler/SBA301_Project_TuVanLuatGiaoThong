// 

package sba.project.tuvanluatgiaothong.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import sba.project.tuvanluatgiaothong.enums.Role;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    private UUID id = UUID.randomUUID();

    @Column(name = "username", length = 60, unique = true)
    private String usernameAuth;

    @Column(name = "email", length = 320)
    private String email;

    @Column(name = "password", length = 256)
    private String passwordAuth;

    @Column(name = "fullname", length = 100)
    private String fullname;

    @Column(name = "avatar_url", length = 1024)
    private String avatarUrl;

    @Column(name = "birthday")
    private Instant birthDay;

    @Column(name = "is_enable")
    private boolean isEnable = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @PrePersist
    public void prePersist() {
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        this.createdDate = ZonedDateTime.now(zoneId).toInstant();
    }

    // UserDetails methods

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role != null ? role.getAuthorities() : new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return passwordAuth != null ? passwordAuth : "";
    }

    @Override
    public String getUsername() {
        return usernameAuth != null ? usernameAuth : "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // customize if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // customize if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // customize if needed
    }

    @Override
    public boolean isEnabled() {
        return isEnable;
    }

    // Getters and Setters (can be generated via Lombok or IDE)

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsernameAuth() {
        return usernameAuth;
    }

    public void setUsernameAuth(String usernameAuth) {
        this.usernameAuth = usernameAuth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordAuth() {
        return passwordAuth;
    }

    public void setPasswordAuth(String passwordAuth) {
        this.passwordAuth = passwordAuth;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Instant getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Instant birthDay) {
        this.birthDay = birthDay;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setIsEnable(boolean enable) {
        isEnable = enable;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public User(String usernameAuth, String email, String passwordAuth, String fullname, Role role) {
        this.usernameAuth = usernameAuth;
        this.email = email;
        this.passwordAuth = passwordAuth;
        this.fullname = fullname;
        this.role = role;
    }
}
