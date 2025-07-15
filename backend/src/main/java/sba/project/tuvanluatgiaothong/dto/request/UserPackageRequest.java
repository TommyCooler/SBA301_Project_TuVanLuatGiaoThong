package sba.project.tuvanluatgiaothong.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
public class UserPackageRequest {
    UUID userId;
    UUID packageId;
    BigDecimal price;
    Instant transactionDate;
}
