package sba.project.tuvanluatgiaothong.pojo;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "law_types")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LawType {

    @Id
    private UUID id;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "updated_date")
    private Timestamp updatedDate;

    @PrePersist
    public void prePersist() {
        var zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        Instant now = ZonedDateTime.now(zoneId).toInstant();
        this.createdDate = Timestamp.from(now);
        this.updatedDate = Timestamp.from(now);
    }
}
