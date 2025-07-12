package sba.project.tuvanluatgiaothong.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPackageResponse {
    private UUID id;
    private UUID userId;
    private UUID packageId;
    private BigDecimal price;
    private Instant transactionDate;
}
