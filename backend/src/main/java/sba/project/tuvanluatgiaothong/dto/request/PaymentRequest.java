package sba.project.tuvanluatgiaothong.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PaymentRequest {
    private BigDecimal amount;
    private String userId;
    private UUID packageId;
}

