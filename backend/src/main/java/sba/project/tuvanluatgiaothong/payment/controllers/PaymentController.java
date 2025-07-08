package sba.project.tuvanluatgiaothong.payment.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sba.project.tuvanluatgiaothong.enums.TransactionMethod;
import sba.project.tuvanluatgiaothong.payment.dtos.PurchaseRequestDTO;
import sba.project.tuvanluatgiaothong.payment.dtos.VNPayResponseDTO;
import sba.project.tuvanluatgiaothong.payment.services.PaymentFactory;
import sba.project.tuvanluatgiaothong.payment.services.PaymentService;

import java.util.Map;

@RestController
@RequestMapping("api/vnpay")
public class PaymentController {

    @Autowired
    private PaymentFactory paymentFactory;

    @PostMapping("/payment")
    public ResponseEntity<String> createPayment(HttpServletRequest request, @RequestBody PurchaseRequestDTO requestDTO) {
        PaymentService paymentService = paymentFactory.getPaymentService(requestDTO.getTransactionMethod());
        String paymentUrl = paymentService.createPaymentUrl(request, requestDTO);
        return ResponseEntity.ok("redirect:" + paymentUrl);
    }

    @GetMapping("/return")
    public ResponseEntity<VNPayResponseDTO> paymentReturn(@RequestParam Map<String, String> vnp_Params) {
        PaymentService paymentService = paymentFactory.getPaymentService(TransactionMethod.VNPAY);
        VNPayResponseDTO response = paymentService.processPaymentReturn(vnp_Params);
        return ResponseEntity.status(response.getStatus().equals("SUCCESS") ? 200 : 400).body(response);
    }

    @PostMapping("/ipn")
    public ResponseEntity<VNPayResponseDTO> ipn(@RequestParam Map<String, String> vnp_Params) {
        PaymentService paymentService = paymentFactory.getPaymentService(TransactionMethod.VNPAY);
        VNPayResponseDTO response = paymentService.processIPN(vnp_Params);
        return ResponseEntity.ok(response);
    }
}
