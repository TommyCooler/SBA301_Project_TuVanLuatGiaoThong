// package sba.project.tuvanluatgiaothong.service;

// import jakarta.transaction.Transactional;
// import lombok.RequiredArgsConstructor;
// import sba.project.tuvanluatgiaothong.dto.request.TransactionHistoryRequest;
// import sba.project.tuvanluatgiaothong.dto.response.TransactionCompletionResponse;
// import sba.project.tuvanluatgiaothong.enums.PayType;
// import sba.project.tuvanluatgiaothong.enums.Status;
// import sba.project.tuvanluatgiaothong.exception.CustomExceptions;
// import sba.project.tuvanluatgiaothong.mapper.TransactionHistoryMapper;
// import sba.project.tuvanluatgiaothong.pojo.TransactionHistory;
// import sba.project.tuvanluatgiaothong.pojo.UserPackage;
// import sba.project.tuvanluatgiaothong.repository.IMomoPaymentService;
// import sba.project.tuvanluatgiaothong.repository.IUserPackageTransaction;
// import sba.project.tuvanluatgiaothong.repository.TransactionHistoryTransaction;
// import sba.project.tuvanluatgiaothong.repository.UsagePackageRepository;
// import sba.project.tuvanluatgiaothong.repository.UserPackageRepository;
// import sba.project.tuvanluatgiaothong.utils.HashingUtil;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Service;
// import org.springframework.web.client.RestTemplate;

// import javax.crypto.Mac;
// import javax.crypto.spec.SecretKeySpec;
// import java.math.BigDecimal;
// import java.time.Instant;
// import java.time.temporal.ChronoUnit;
// import java.util.LinkedHashMap;
// import java.util.Map;
// import java.util.UUID;

// @Service
// @RequiredArgsConstructor
// public class MomoPaymentService implements IMomoPaymentService {

//     @Value("${momo.partner-code}") private String partnerCode;
//     @Value("${momo.access-key}") private String accessKey;
//     @Value("${momo.secret-key}") private String secretKey;
//     @Value("${momo.endpoint}") private String endpoint;
//     @Value("${momo.redirect-url}") private String redirectUrl;
//     @Value("${momo.ipn-url}") private String ipnUrl;

//     private final TransactionHistoryTransaction transactionHistoryTransaction;

//     private final HashingUtil hashingUtil;

//     private final RedisTemplate<String, Object> redisTemplate;

//     private final IUserPackageTransaction transactionUserPackage;

//     private final UserPackageRepository userPackageRepository;

//     private final UsagePackageRepository usagePackageRepository;

//     private final TransactionHistoryMapper transactionHistoryMapper;


//     public String createPaymentUrl(BigDecimal amount, String userId, UUID packageId) {
//         // Decode userId from String to UUID
//         UUID decodedUserId = UUID.fromString(this.hashingUtil.decode(userId));
//         // Create a unique order ID using UUID and current timestamp
//         String orderId = UUID.randomUUID() + "-" + Instant.now().toString();
//         // Create user package order info without enable
//         UserPackage userPackage = UserPackage.builder()
//                 .id(UUID.randomUUID())
//                 .orderId(orderId)
//                 .userId(decodedUserId)
//                 .packageId(packageId)
//                 .price(amount)
//                 .isEnable(Boolean.FALSE)
//                 .build();
//         this.transactionUserPackage.save(userPackage);

//         String requestId = UUID.randomUUID().toString();
//         String orderInfo = "Payment for order " + orderId;

//         String rawHash = "accessKey=" + accessKey +
//                 "&amount=" + amount +
//                 "&extraData=" + decodedUserId +
//                 "&ipnUrl=" + ipnUrl +
//                 "&orderId=" + orderId +
//                 "&orderInfo=" + orderInfo +
//                 "&partnerCode=" + partnerCode +
//                 "&redirectUrl=" + redirectUrl +
//                 "&requestId=" + requestId +
//                 "&requestType=captureWallet";

//         String signature = hmacSHA256(rawHash, secretKey);

//         Map<String, Object> body = new LinkedHashMap<>();

//         body.put("partnerCode", partnerCode);
//         body.put("accessKey", accessKey);
//         body.put("requestId", requestId);
//         body.put("amount", amount.toString());
//         body.put("orderId", orderId);
//         body.put("orderInfo", orderInfo);
//         body.put("redirectUrl", redirectUrl);
//         body.put("ipnUrl", ipnUrl);
//         body.put("extraData", decodedUserId.toString());
//         body.put("requestType", "captureWallet");
//         body.put("signature", signature);
//         body.put("lang", "vi");

//         RestTemplate restTemplate = new RestTemplate();
//         ResponseEntity<Map> response = restTemplate.postForEntity(endpoint, body, Map.class);

//         Map<String, Object> resBody = response.getBody();
//         System.out.println("Momo API response: " + resBody);

//         if (resBody == null || resBody.get("payUrl") == null) {
//             throw new RuntimeException("Momo API failed or did not return payUrl. Full response: " + resBody);
//         }

//         return resBody.get("payUrl").toString();
//     }

//     //tao chu ky bao mat
//     private String hmacSHA256(String data, String key) {
//        try {
//            Mac hmac = Mac.getInstance("HmacSHA256");
//            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(),"HmacSHA256");
//            hmac.init(secretKeySpec);
//            byte[] hash = hmac.doFinal(data.getBytes());
//            return bytesToHex(hash);
//        } catch (Exception e) {
//            throw new RuntimeException("Error generating HMAC SHA256", e);
//        }
//     }

//     @Override
//     @Transactional
//     public TransactionCompletionResponse handleIpnPayload(Map<String, String> payload) {
//         try {
//             if (payload.get("orderId") == null || payload.get("amount") == null) {
//                 throw new IllegalArgumentException("Missing required fields");
//             }
//             String orderId = payload.get("orderId");
//             TransactionHistoryRequest dto = new TransactionHistoryRequest();
//             dto.setOrderId(orderId);
//             dto.setPaymentTransId(payload.get("transId"));
//             dto.setAmount(new BigDecimal(payload.get("amount")));
//             dto.setPayType(PayType.MOMO);
//             dto.setStatus("0".equals(payload.get("resultCode")) ? Status.SUCCESS : Status.FAILED);
//             dto.setMessage(payload.get("message"));
//             dto.setPaidAt(Instant.now());
//             dto.setUserId(UUID.fromString(payload.get("extraData")));
//             TransactionHistory savedTransaction = transactionHistoryTransaction.save(transactionHistoryMapper.toEntity(dto));

//             // Disable old user package if exists
//             this.transactionUserPackage.disableAllOldPackageOfUser(savedTransaction.getUserId());
//             // Enable the current bought package if payment is successful
//             UserPackage userPackage = this.userPackageRepository.findByOrderId(orderId).orElseThrow(
//                     () -> new CustomExceptions.ResourceNotFoundException("Cannot find user package with orderId: " + orderId)
//             );
//             userPackage.setEnable(Boolean.TRUE);
//             // Update expired date based on the package's day limit
//             userPackage.setExpiredDate(
//                     userPackage.getTransactionDate().plus(
//                             usagePackageRepository.getDayLimitOfUsagePackageById(userPackage.getPackageId()),
//                             ChronoUnit.DAYS
//                     )
//             );
//             this.transactionUserPackage.save(userPackage);
//             // Clear tracking limitation cache for the user
//             if (redisTemplate.hasKey(userPackage.getUserId().toString())) {
//                 redisTemplate.delete(userPackage.getUserId().toString());
//             }
//             return this.transactionHistoryMapper.toTransactionCompletionResponse(
//                     savedTransaction,
//                     this.usagePackageRepository.findById(userPackage.getPackageId()).orElseThrow(
//                             () -> new CustomExceptions.ResourceNotFoundException(
//                                     "Cannot find usage package with id: " + userPackage.getPackageId()
//                             ))
//             );
//         }
//         catch (Exception e) {
//             throw new CustomExceptions.InternalServerException("Error processing Momo IPN payload: " + e.getMessage());
//         }
//     }

//     //chuyen doi byte sang hex
//     //can vi dung trong ham hmacSHA256
//     //momo ko nhan duoc ma nhi phan
//     private String bytesToHex(byte[] bytes) {
//         StringBuilder hexString = new StringBuilder();
//         for (byte b : bytes) {
//             String hex = Integer.toHexString(0xff & b);
//             if (hex.length() == 1) hexString.append('0');
//             hexString.append(hex);
//         }
//         return hexString.toString();
//     }
// }


package sba.project.tuvanluatgiaothong.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import sba.project.tuvanluatgiaothong.dto.request.TransactionHistoryRequest;
import sba.project.tuvanluatgiaothong.dto.response.TransactionCompletionResponse;
import sba.project.tuvanluatgiaothong.dto.response.UsagePackageResponse;
import sba.project.tuvanluatgiaothong.enums.PayType;
import sba.project.tuvanluatgiaothong.enums.Status;
import sba.project.tuvanluatgiaothong.exception.CustomExceptions;
import sba.project.tuvanluatgiaothong.mapper.TransactionHistoryMapper;
import sba.project.tuvanluatgiaothong.pojo.TransactionHistory;
import sba.project.tuvanluatgiaothong.pojo.UsagePackage;
import sba.project.tuvanluatgiaothong.pojo.UserPackage;
import sba.project.tuvanluatgiaothong.repository.*;
import sba.project.tuvanluatgiaothong.utils.HashingUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MomoPaymentService implements IMomoPaymentService {

    @Value("${momo.partner-code}")
    private String partnerCode;
    @Value("${momo.access-key}")
    private String accessKey;
    @Value("${momo.secret-key}")
    private String secretKey;
    @Value("${momo.endpoint}")
    private String endpoint;
    @Value("${momo.redirect-url}")
    private String redirectUrl;
    @Value("${momo.ipn-url}")
    private String ipnUrl;

    private final HistoryTransactionTransaction historyTransactionTransaction;

    private final HashingUtil hashingUtil;

    private final RedisTemplate<String, Object> redisTemplate;

    private final IUserPackageTransaction transactionUserPackage;

    private final UserPackageRepository userPackageRepository;

    private final UsagePackageRepository usagePackageRepository;

    private final TransactionHistoryMapper transactionHistoryMapper;


    public String createPaymentUrl(BigDecimal amount, String userId, UUID packageId) {
        // Decode userId from String to UUID
        UUID decodedUserId = UUID.fromString(this.hashingUtil.decode(userId));
        // Create a unique order ID using UUID and current timestamp
        String orderId = UUID.randomUUID() + "-" + Instant.now().toString();
        // Create user package order info without enable
        UserPackage userPackage = UserPackage.builder()
                .id(UUID.randomUUID())
                .orderId(orderId)
                .userId(decodedUserId)
                .packageId(packageId)
                .price(amount)
                .isEnable(Boolean.FALSE)
                .build();
        this.transactionUserPackage.save(userPackage);

        String requestId = UUID.randomUUID().toString();
        String orderInfo = "Payment for order " + orderId;

        String rawHash = "accessKey=" + accessKey +
                "&amount=" + amount +
                "&extraData=" + decodedUserId +
                "&ipnUrl=" + ipnUrl +
                "&orderId=" + orderId +
                "&orderInfo=" + orderInfo +
                "&partnerCode=" + partnerCode +
                "&redirectUrl=" + redirectUrl +
                "&requestId=" + requestId +
                "&requestType=captureWallet";

        String signature = hmacSHA256(rawHash, secretKey);

        Map<String, Object> body = new LinkedHashMap<>();

        body.put("partnerCode", partnerCode);
        body.put("accessKey", accessKey);
        body.put("requestId", requestId);
        body.put("amount", amount.toString());
        body.put("orderId", orderId);
        body.put("orderInfo", orderInfo);
        body.put("redirectUrl", redirectUrl);
        body.put("ipnUrl", ipnUrl);
        body.put("extraData", decodedUserId.toString());
        body.put("requestType", "captureWallet");
        body.put("signature", signature);
        body.put("lang", "vi");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(endpoint, body, Map.class);

        Map<String, Object> resBody = response.getBody();
        System.out.println("Momo API response: " + resBody);

        if (resBody == null || resBody.get("payUrl") == null) {
            throw new RuntimeException("Momo API failed or did not return payUrl. Full response: " + resBody);
        }

        return resBody.get("payUrl").toString();
    }

    //tao chu ky bao mat
    private String hmacSHA256(String data, String key) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            hmac.init(secretKeySpec);
            byte[] hash = hmac.doFinal(data.getBytes());
            return bytesToHex(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error generating HMAC SHA256", e);
        }
    }

    @Override
    @Transactional
    public TransactionCompletionResponse handleIpnPayload(Map<String, String> payload) {
        try {
            if (payload.get("orderId") == null || payload.get("amount") == null) {
                throw new IllegalArgumentException("Missing required fields");
            }

            if (!"0".equals(payload.get("resultCode"))){
                throw new IllegalArgumentException("Payment cancellation");
            }

            String orderId = payload.get("orderId");
            TransactionHistoryRequest dto = new TransactionHistoryRequest();
            dto.setOrderId(orderId);
            dto.setPaymentTransId(payload.get("transId"));
            dto.setAmount(new BigDecimal(payload.get("amount")));
            dto.setPayType(PayType.MOMO);
            dto.setStatus("0".equals(payload.get("resultCode")) ? Status.SUCCESS : Status.FAILED);
            dto.setMessage(payload.get("message"));
            dto.setPaidAt(Instant.now());
            dto.setUserId(UUID.fromString(payload.get("extraData")));
            TransactionHistory savedTransaction = historyTransactionTransaction.save(transactionHistoryMapper.toEntity(dto));

            // Disable old user package if exists
            this.transactionUserPackage.disableAllOldPackageOfUser(savedTransaction.getUserId());
            // Enable the current bought package if payment is successful
            UserPackage userPackage = this.userPackageRepository.findByOrderId(orderId).orElseThrow(
                    () -> new CustomExceptions.ResourceNotFoundException("Cannot find user package with orderId: " + orderId)
            );
            userPackage.setEnable(Boolean.TRUE);
            // Update expired date based on the package's day limit
            userPackage.setExpiredDate(
                    userPackage.getTransactionDate().plus(
                            usagePackageRepository.getDayLimitOfUsagePackageById(userPackage.getPackageId()),
                            ChronoUnit.DAYS
                    )
            );
            this.transactionUserPackage.save(userPackage);
            // Clear tracking limitation cache for the user
            if (redisTemplate.hasKey(userPackage.getUserId().toString())) {
                redisTemplate.delete(userPackage.getUserId().toString());
            }
            return this.transactionHistoryMapper.toTransactionCompletionResponse(
                    savedTransaction,
                    this.usagePackageRepository.findById(userPackage.getPackageId()).orElseThrow(
                            () -> new CustomExceptions.ResourceNotFoundException(
                                    "Cannot find usage package with id: " + userPackage.getPackageId()
                            ))
            );
        }
        catch (Exception e) {
            throw new CustomExceptions.InternalServerException("Error processing Momo IPN payload: " + e.getMessage());
        }
    }

    //chuyen doi byte sang hex
    //can vi dung trong ham hmacSHA256
    //momo ko nhan duoc ma nhi phan
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
