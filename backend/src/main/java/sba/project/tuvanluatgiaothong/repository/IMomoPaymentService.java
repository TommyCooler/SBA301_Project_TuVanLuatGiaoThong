package sba.project.tuvanluatgiaothong.repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import sba.project.tuvanluatgiaothong.dto.response.TransactionCompletionResponse;

public interface IMomoPaymentService {

    //tao url thanh toan momo ok chua
    String createPaymentUrl(BigDecimal amount, String userId, UUID packageId);

    TransactionCompletionResponse handleIpnPayload(Map<String, String> payload);

}
