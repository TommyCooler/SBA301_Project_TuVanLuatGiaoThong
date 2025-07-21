package sba.project.tuvanluatgiaothong.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Table(name = "comments")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name="username")
    private String username;

    @Column(name = "fullname", columnDefinition = "NVARCHAR(20)")
    private String fullname;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "content",columnDefinition = "NVARCHAR(MAX)", nullable = false)
    private String content;

    @Column(name = "is_anonymous")
    private boolean isAnonymous;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "created_date", updatable = false)
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @PrePersist
    public void prePersist() {
        var zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        this.createdDate = ZonedDateTime.now(zoneId).toInstant();
    }

}
