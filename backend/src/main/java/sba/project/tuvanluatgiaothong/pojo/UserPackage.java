package sba.project.tuvanluatgiaothong.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sba.project.tuvanluatgiaothong.enums.TransactionMethod;
import sba.project.tuvanluatgiaothong.enums.UserPackageStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_packages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "usage_package_id", nullable = false)
    private UsagePackage usagePackage;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_method")
    private TransactionMethod transactionMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserPackageStatus status;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
}
