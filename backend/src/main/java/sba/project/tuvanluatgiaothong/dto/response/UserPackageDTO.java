package sba.project.tuvanluatgiaothong.dto.response;

import lombok.Data;
import sba.project.tuvanluatgiaothong.enums.TransactionMethod;
import sba.project.tuvanluatgiaothong.enums.UserPackageStatus;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserPackageDTO {
    private Long userPackageId;
    private UUID userId;
    private String userName;
    private UUID usagePackageId;
    private String packageName;
    private Float packagePrice;
    private Integer dailyLimit;
    private Integer daysLimit;
    private LocalDateTime transactionDate;
    private TransactionMethod transactionMethod;
    private LocalDateTime expirationDate;
    private UserPackageStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}

