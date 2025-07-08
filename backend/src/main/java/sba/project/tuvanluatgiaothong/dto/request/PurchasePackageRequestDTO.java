package sba.project.tuvanluatgiaothong.dto.request;

import lombok.Data;
import sba.project.tuvanluatgiaothong.enums.TransactionMethod;

import java.util.UUID;

@Data
public class PurchasePackageRequestDTO {
    private UUID userId;
    private UUID usagePackageId;
    private TransactionMethod transactionMethod;
}
