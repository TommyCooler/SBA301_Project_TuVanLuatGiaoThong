package sba.project.tuvanluatgiaothong.pojo;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@Table(name = "laws")
@AllArgsConstructor
@NoArgsConstructor
public class Law {
    
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private UUID id = UUID.randomUUID();

    @Column(name = "reference_number", length = 255)
    private String referenceNumber;

    @Column(name = "dateline", length = 255)
    private String dateline;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "issue_date")
    private Timestamp issueDate;

    @Column(name = "effective_date")
    private Timestamp effectiveDate;
    
    @Column(name = "source_url", columnDefinition = "TEXT")
    private String sourceUrl;

    @Column(name = "file_path", columnDefinition = "TEXT")
    private String filePath;

    @Column(name = "isDelete")
    private boolean isDeleted;
    
    @ManyToOne
    @JoinColumn(name = "lawType")
    private LawType lawType;

    @Column(name = "create_date")
    private Timestamp createdDate;

    @Column(name = "update_date")
    private Timestamp updatedDate;

    @PrePersist
    public void prePersist() {
    var zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
    Instant now = ZonedDateTime.now(zoneId).toInstant();
    this.createdDate = Timestamp.from(now);
}

    @PreUpdate
    public void preUpdate() {
        var zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        Instant now = ZonedDateTime.now(zoneId).toInstant();
        this.updatedDate = Timestamp.from(now);
    }
}
