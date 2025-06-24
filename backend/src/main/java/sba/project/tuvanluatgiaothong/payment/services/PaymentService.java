package sba.project.tuvanluatgiaothong.payment.services;

import jakarta.servlet.http.HttpServletRequest;
import sba.project.tuvanluatgiaothong.payment.dtos.PurchaseRequestDTO;
import sba.project.tuvanluatgiaothong.payment.dtos.VNPayResponseDTO;

import java.util.Map;

public interface PaymentService {
    String createPaymentUrl(HttpServletRequest request, PurchaseRequestDTO requestDTO);
    VNPayResponseDTO processPaymentReturn(Map<String, String> params);
    VNPayResponseDTO processIPN(Map<String, String> params);
}
