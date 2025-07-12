//package sba.project.tuvanluatgiaothong.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import sba.project.tuvanluatgiaothong.dto.request.PurchasePackageRequestDTO;
//import sba.project.tuvanluatgiaothong.dto.response.UserPackageDTO;
//import sba.project.tuvanluatgiaothong.service.IUserPackageService;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/subscriptions")
//@RequiredArgsConstructor
//public class UserPackageController {
//    private final IUserPackageService userPackageService;
//
//    @PostMapping("/purchase")
//    public ResponseEntity<UserPackageDTO> recordPurchase(@RequestBody PurchasePackageRequestDTO request) {
//        return ResponseEntity.ok(userPackageService.recordPurchase(request));
//    }
//
//    @PatchMapping("/{id}/block")
//    public ResponseEntity<Void> blockUserPackage(@PathVariable Long id) {
//        userPackageService.blockUserPackage(id);
//        return ResponseEntity.ok().build();
//    }
//
//    @PatchMapping("/{id}/unblock")
//    public ResponseEntity<Void> unblockUserPackage(@PathVariable Long id) {
//        userPackageService.unblockUserPackage(id);
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping("/user/{userId}/active")
//    public ResponseEntity<List<UserPackageDTO>> getUserActiveSubscriptions(@PathVariable UUID userId) {
//        return ResponseEntity.ok(userPackageService.getUserActiveSubscriptions(userId));
//    }
//
//    @GetMapping("/user/{userId}/history")
//    public ResponseEntity<List<UserPackageDTO>> getUserSubscriptionHistory(@PathVariable UUID userId) {
//        return ResponseEntity.ok(userPackageService.getUserSubscriptionHistory(userId));
//    }
//
//    @GetMapping("/expired")
//    public ResponseEntity<List<UserPackageDTO>> getExpiredSubscriptions() {
//        return ResponseEntity.ok(userPackageService.getExpiredSubscriptions());
//    }
//
//    @GetMapping("/search")
//    public ResponseEntity<List<UserPackageDTO>> searchByPackageName(@RequestParam String packageName) {
//        return ResponseEntity.ok(userPackageService.searchByPackageName(packageName));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<UserPackageDTO>> findAllSubscription() {
//        return ResponseEntity.ok(userPackageService.findAllSubscription());
//    }
//}


package sba.project.tuvanluatgiaothong.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import sba.project.tuvanluatgiaothong.dto.request.UserPackageRequest;
import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
import sba.project.tuvanluatgiaothong.dto.response.UserPackageResponse;
import sba.project.tuvanluatgiaothong.service.IUserPackageService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user-packages/user-package")
public class UserPackageController {

    private final IUserPackageService userPackageService;

    @GetMapping("/health")
    public String healthCheck() {
        return "User Package Service is running!";
    }

    @PostMapping("/admin/create")
    public ResponseEntity<ApiResponse<?>> createUserPackage(@RequestBody UserPackageRequest userPackageRequest) {
        UserPackageResponse responseDto = userPackageService.createUserPackage(userPackageRequest);
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .status("success")
                        .message("User package created successfully")
                        .dataResponse(responseDto)
                        .build(),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<?>> getUserPackageById(@PathVariable UUID id) {
        UserPackageResponse responseDto = userPackageService.getUserPackageById(id);
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .status("success")
                        .message("User package retrieved successfully")
                        .dataResponse(responseDto)
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<?>> getAllUserPackages() {
        var responseDtoList = userPackageService.getAllUserPackages();
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .status("success")
                        .message("All user packages retrieved successfully")
                        .dataResponse(responseDtoList)
                        .build(),
                HttpStatus.OK
        );
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<ApiResponse<?>> updateUserPackage(@PathVariable UUID id, @RequestBody UserPackageRequest userPackageRequest) {
        UserPackageResponse responseDto = userPackageService.updateUserPackage(id, userPackageRequest);
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .status("success")
                        .message("User package updated successfully")
                        .dataResponse(responseDto)
                        .build(),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteUserPackage(@PathVariable UUID id) {
        userPackageService.deleteUserPackage(id);
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .status("success")
                        .message("User package deleted successfully")
                        .build(),
                HttpStatus.OK
        );
    }


}
