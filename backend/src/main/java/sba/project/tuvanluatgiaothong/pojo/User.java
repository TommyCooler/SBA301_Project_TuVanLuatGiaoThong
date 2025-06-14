package sba.project.tuvanluatgiaothong.pojo;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sba.project.tuvanluatgiaothong.enums.UserRole;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "id")
    private UUID id = UUID.randomUUID();

    @Column(name = "email", nullable = false, unique = true, length = 320)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "fullName", nullable = false, length = 100)
    private String fullName;

    @Column(name = "avatar_url", length = 1024)
    private String avatarUrl;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "is_enable", nullable = false)
    private boolean isEnable;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
