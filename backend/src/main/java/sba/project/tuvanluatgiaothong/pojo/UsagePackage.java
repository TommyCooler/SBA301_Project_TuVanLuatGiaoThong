//package sba.project.tuvanluatgiaothong.pojo;
//
//import jakarta.persistence.*;
//import lombok.*;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.UUID;
//
//@Entity
//@Table(name = "usage_packages")
//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class UsagePackage {
//
//    @Id
//    @Column(name = "usage_package_id")
//    private UUID usagePackageId;
//
//    @Column(name = "name", length = 60)
//    private String name;
//
//    @Column(name = "description", columnDefinition = "text")
//    private String description;
//
//    @Column(name = "price")
//    private Float price;
//
//    @Column(name = "daily_limit")
//    private Integer dailyLimit;
//
//    @Column(name = "days_limit")
//    private Integer daysLimit;
//
//    @Column(name = "is_enable")
//    private Boolean isEnable;
//
//    @Column(name = "created_date")
//    private LocalDateTime createdDate;
//
//    @Column(name = "update_date")
//    private LocalDateTime updateDate;
//
//    @OneToMany(mappedBy = "usagePackage", cascade = CascadeType.ALL)
//    private List<UserPackage> userPackages;
//
//}


package sba.project.tuvanluatgiaothong.pojo;

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
@Table(name = "usage_packages")
@NoArgsConstructor
@AllArgsConstructor
public class UsagePackage {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "name", length = 60, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price")
    private float price;

    @Column(name = "daily_limit")
    private int dailyLimit;

    @Column(name = "days_limit")
    private int daysLimit;

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
