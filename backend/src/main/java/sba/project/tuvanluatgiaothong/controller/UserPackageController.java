package sba.project.tuvanluatgiaothong.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sba.project.tuvanluatgiaothong.dto.request.PurchasePackageRequestDTO;
import sba.project.tuvanluatgiaothong.dto.response.UserPackageDTO;
import sba.project.tuvanluatgiaothong.service.IUserPackageService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class UserPackageController {
    private final IUserPackageService userPackageService;

    @PostMapping("/purchase")
    public ResponseEntity<UserPackageDTO> recordPurchase(@RequestBody PurchasePackageRequestDTO request) {
        return ResponseEntity.ok(userPackageService.recordPurchase(request));
    }

    @PatchMapping("/{id}/block")
    public ResponseEntity<Void> blockUserPackage(@PathVariable Long id) {
        userPackageService.blockUserPackage(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/unblock")
    public ResponseEntity<Void> unblockUserPackage(@PathVariable Long id) {
        userPackageService.unblockUserPackage(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<UserPackageDTO>> getUserActiveSubscriptions(@PathVariable UUID userId) {
        return ResponseEntity.ok(userPackageService.getUserActiveSubscriptions(userId));
    }

    @GetMapping("/user/{userId}/history")
    public ResponseEntity<List<UserPackageDTO>> getUserSubscriptionHistory(@PathVariable UUID userId) {
        return ResponseEntity.ok(userPackageService.getUserSubscriptionHistory(userId));
    }

    @GetMapping("/expired")
    public ResponseEntity<List<UserPackageDTO>> getExpiredSubscriptions() {
        return ResponseEntity.ok(userPackageService.getExpiredSubscriptions());
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserPackageDTO>> searchByPackageName(@RequestParam String packageName) {
        return ResponseEntity.ok(userPackageService.searchByPackageName(packageName));
    }

    @GetMapping
    public ResponseEntity<List<UserPackageDTO>> findAllSubscription() {
        return ResponseEntity.ok(userPackageService.findAllSubscription());
    }
}