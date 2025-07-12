package sba.project.tuvanluatgiaothong.dto.request;

import lombok.Data;
import sba.project.tuvanluatgiaothong.enums.PayType;
import sba.project.tuvanluatgiaothong.enums.Status;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
public class TransactionHistoryRequest {
    private String orderId;
    private UUID userId;
    private String paymentTransId;
    private PayType payType;
    private BigDecimal amount;
    private Status status;
    private String message;
    private Instant paidAt;
}
