package sba.project.tuvanluatgiaothong.pojo;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
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
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "issue_date")
    private Timestamp issue_date;

    @Column(name = "effective_date")
    private Timestamp effective_date;
    
    @Column(name = "source_url", columnDefinition = "TEXT")
    private String sourceUrl;

    @Column(name = "file_path", columnDefinition = "TEXT")
    private String filePath;

    @Column(name = "isDelete")
    private boolean isDeleted;
    
    @ManyToOne
    @JoinColumn(name = "lawType")
    private LawType lawType;

    @Column(name = "createDate")
    private Timestamp createdDate;

    @Column(name = "updateDate")
    private Timestamp updatedDate;

    @PrePersist
public void prePersist() {
    var zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
    Instant now = ZonedDateTime.now(zoneId).toInstant();
    this.createdDate = Timestamp.from(now);
    this.updatedDate = Timestamp.from(now);
}
}
