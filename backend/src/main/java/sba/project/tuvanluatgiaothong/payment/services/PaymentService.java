package sba.project.tuvanluatgiaothong.payment.services;

import jakarta.servlet.http.HttpServletRequest;
import sba.project.tuvanluatgiaothong.payment.dtos.PurchasePaymentRequestDTO;

import java.util.Map;

public interface PaymentService {
    String createPaymentUrl(HttpServletRequest request, PurchasePaymentRequestDTO requestDTO);
    Object processPaymentReturn(Map<String, String> params);
    Object processIPN(Map<String, String> params);
}
