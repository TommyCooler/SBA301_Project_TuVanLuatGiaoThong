// package sba.project.tuvanluatgiaothong.payment.controllers;

// import jakarta.servlet.http.HttpServletRequest;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import sba.project.tuvanluatgiaothong.enums.TransactionMethod;
// import sba.project.tuvanluatgiaothong.payment.dtos.MoMoResponseDTO;
// import sba.project.tuvanluatgiaothong.payment.dtos.PurchasePaymentRequestDTO;
// import sba.project.tuvanluatgiaothong.payment.dtos.VNPayResponseDTO;
// import sba.project.tuvanluatgiaothong.payment.services.PaymentFactory;
// import sba.project.tuvanluatgiaothong.payment.services.PaymentService;

// import java.util.Map;

// @RestController
// @RequestMapping("/api/v1/payment")
// public class PaymentController {

//     @Autowired
//     private PaymentFactory paymentFactory;

//     @PostMapping("/payment")
//     public ResponseEntity<String> createPayment(HttpServletRequest request, @RequestBody PurchasePaymentRequestDTO requestDTO) {
//         PaymentService paymentService = paymentFactory.getPaymentService(requestDTO.getTransactionMethod());
//         String paymentUrl = paymentService.createPaymentUrl(request, requestDTO);
//         return ResponseEntity.ok("redirect:" + paymentUrl);
//     }

//     @GetMapping("/return")
//     public ResponseEntity<?> paymentReturn(@RequestParam Map<String, String> params) {
//         TransactionMethod method = params.containsKey("vnp_TxnRef") ? TransactionMethod.VNPAY : TransactionMethod.MOMO;
//         PaymentService paymentService = paymentFactory.getPaymentService(method);
//         Object response = paymentService.processPaymentReturn(params);
//         if (response instanceof VNPayResponseDTO) {
//             VNPayResponseDTO vnPayResponse = (VNPayResponseDTO) response;
//             return ResponseEntity.status(vnPayResponse.getStatus().equals("SUCCESS") ? 200 : 400).body(vnPayResponse);
//         } else {
//             MoMoResponseDTO moMoResponse = (MoMoResponseDTO) response;
//             return ResponseEntity.status(moMoResponse.getStatus().equals("SUCCESS") ? 200 : 400).body(moMoResponse);
//         }
//     }

//     @PostMapping("/ipn")
//     public ResponseEntity<?> ipn(@RequestParam Map<String, String> params) {
//         TransactionMethod method = params.containsKey("vnp_TxnRef") ? TransactionMethod.VNPAY : TransactionMethod.MOMO;
//         PaymentService paymentService = paymentFactory.getPaymentService(method);
//         Object response = paymentService.processIPN(params);
//         return ResponseEntity.ok(response);
//     }
// }
