//package sba.project.tuvanluatgiaothong.pojo;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import sba.project.tuvanluatgiaothong.enums.TransactionMethod;
//import sba.project.tuvanluatgiaothong.enums.UserPackageStatus;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "user_packages")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class UserPackage {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "usage_package_id", nullable = false)
//    private UsagePackage usagePackage;
//
//    @Column(name = "transaction_date")
//    private LocalDateTime transactionDate;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "transaction_method")
//    private TransactionMethod transactionMethod;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "status")
//    private UserPackageStatus status;
//
//    @Column(name = "expiration_date")
//    private LocalDateTime expirationDate;
//
//    @Column(name = "created_date")
//    private LocalDateTime createdDate;
//
//    @Column(name = "updated_date")
//    private LocalDateTime updatedDate;
//}

package sba.project.tuvanluatgiaothong.pojo;

import java.math.BigDecimal;
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
@Table(name = "user_packages")
@NoArgsConstructor
@AllArgsConstructor
public class UserPackage {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id = UUID.randomUUID();

    @Column(name = "order_id", nullable = false, updatable = false)
    private String orderId;

    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @Column(name = "package_id", nullable = false, updatable = false)
    private UUID packageId;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "transaction_date")
    private Instant transactionDate;

    @Column(name = "expired_date")
    private Instant expiredDate;

    @Column(name = "is_enable", nullable = false)
    private boolean isEnable;

    @PrePersist
    public void prePersist() {
        var zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        this.transactionDate = ZonedDateTime.now(zoneId).toInstant();
    }
}

