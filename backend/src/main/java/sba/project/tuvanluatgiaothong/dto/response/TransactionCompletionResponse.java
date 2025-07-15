package sba.project.tuvanluatgiaothong.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class TransactionCompletionResponse {

    private String orderId;
    private BigDecimal amount;
    private String status;
    private String message;
    private Instant paidAt;

    private UsagePackageResponse usagePackage;

}
