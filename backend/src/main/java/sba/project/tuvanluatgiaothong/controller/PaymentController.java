package sba.project.tuvanluatgiaothong.controller;

import jakarta.servlet.http.HttpServletRequest;
import sba.project.tuvanluatgiaothong.dto.request.PaymentRequest;
import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
import sba.project.tuvanluatgiaothong.repository.IMomoPaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user-packages/payment")
public class PaymentController {
    @Autowired
    private final IMomoPaymentService momoPaymentService;

    public PaymentController(IMomoPaymentService momoPaymentService) {
        this.momoPaymentService = momoPaymentService;
    }

    @PostMapping("/momo")
    public ResponseEntity<Map<String, String>> payWithMomo(@RequestBody PaymentRequest request) {
        String payUrl = momoPaymentService.createPaymentUrl(
//                request.getOrderId(),
                request.getAmount(),
                request.getUserId(),
                request.getPackageId()
        );
        return ResponseEntity.ok(Map.of("payUrl", payUrl));
    }

    @PostMapping("/ipn")
    public ResponseEntity<ApiResponse<?>> momoIpn(@RequestBody Map<String,String> payload,
                                               HttpServletRequest req) {
        System.out.println(">>>> IPN hit: " + req.getMethod() + " " + req.getRequestURI());
        System.out.println(">>>> Headers: " + Collections.list(req.getHeaderNames())
                .stream().collect(Collectors.toMap(h->h, req::getHeader)));
        System.out.println(">>>> Payload: " + payload);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .status("success")
                        .message("IPN received successfully")
                        .dataResponse(momoPaymentService.handleIpnPayload(payload))
                        .build()
        );
    }
}

