package sba.project.tuvanluatgiaothong.pojo;

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
    @Builder.Default
    private UUID id = UUID.randomUUID();

    @Column(name = "name", columnDefinition = "NVARCHAR(30)")
    private String name;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @PrePersist
    public void prePersist() {
        var zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        this.createdDate = ZonedDateTime.now(zoneId).toInstant();
    }
}
