//package sba.project.tuvanluatgiaothong.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import sba.project.tuvanluatgiaothong.dto.request.CreateUsagePackageDTO;
//import sba.project.tuvanluatgiaothong.dto.response.UsagePackageDTO;
//import sba.project.tuvanluatgiaothong.service.IUsagePackageService;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/packages")
//@RequiredArgsConstructor
//public class UsagePackageController {
//    private final IUsagePackageService usagePackageService;
//
//    @GetMapping
//    public ResponseEntity<List<UsagePackageDTO>> getAllPackages() {
//        return ResponseEntity.ok(usagePackageService.getAllPackages());
//    }
//
//    @GetMapping("/active")
//    public ResponseEntity<List<UsagePackageDTO>> getAllActivePackages() {
//        return ResponseEntity.ok(usagePackageService.getAllActivePackages());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<UsagePackageDTO> getPackageById(@PathVariable UUID id) {
//        return ResponseEntity.ok(usagePackageService.getPackageById(id));
//    }
//
//    @GetMapping("/search")
//    public ResponseEntity<List<UsagePackageDTO>> searchPackages(@RequestParam String keyword) {
//        return ResponseEntity.ok(usagePackageService.searchPackages(keyword));
//    }
//
//    @GetMapping("/price-range")
//    public ResponseEntity<List<UsagePackageDTO>> getPackagesWithinPrice(
//            @RequestParam(required = false) Float minPrice,
//            @RequestParam(required = false) Float maxPrice) {
//        return ResponseEntity.ok(usagePackageService.getPackagesWithinPrice(minPrice, maxPrice));
//    }
//
//    @PostMapping
//    public ResponseEntity<UsagePackageDTO> createPackage(@RequestBody CreateUsagePackageDTO dto) {
//        return ResponseEntity.ok(usagePackageService.createPackage(dto));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<UsagePackageDTO> updatePackage(
//            @PathVariable UUID id,
//            @RequestBody CreateUsagePackageDTO dto) {
//        return ResponseEntity.ok(usagePackageService.updatePackage(id, dto));
//    }
//
//    @PatchMapping("/{id}/disable")
//    public ResponseEntity<Void> disablePackage(@PathVariable UUID id) {
//        usagePackageService.disablePackage(id);
//        return ResponseEntity.ok().build();
//    }
//
//    @PatchMapping("/{id}/enable")
//    public ResponseEntity<Void> enablePackage(@PathVariable UUID id) {
//        usagePackageService.enablePackage(id);
//        return ResponseEntity.ok().build();
//    }
//}

package sba.project.tuvanluatgiaothong.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sba.project.tuvanluatgiaothong.dto.request.UsagePackageRequest;
import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
import sba.project.tuvanluatgiaothong.service.IUsagePackageService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user-packages/usage-package")
public class UsagePackageController {

    private final IUsagePackageService usagePackageService;

    @PostMapping("/admin/create")
    public ResponseEntity<ApiResponse<?>> createUsagePackage(@RequestBody UsagePackageRequest usagePackageRequest) {
        return new ResponseEntity<>(this.usagePackageService.createUsagePackage(usagePackageRequest), HttpStatus.CREATED);
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<ApiResponse<?>> updateUsagePackage(
            @PathVariable("id") UUID id, @RequestBody UsagePackageRequest usagePackageRequest
    ) {
        return new ResponseEntity<>(this.usagePackageService.updateUsagePackage(id, usagePackageRequest), HttpStatus.OK);
    }

    @PutMapping("/admin/deactivate/{id}")
    public ResponseEntity<ApiResponse<?>> deactivateUsagePackage(@PathVariable("id") UUID id ) {
        return new ResponseEntity<>(this.usagePackageService.deactivateUsagePackage(id), HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<?>> getAllUsagePackages() {
        return new ResponseEntity<>(this.usagePackageService.getAllUsagePackage(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<?>> getUsagePackageById(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(this.usagePackageService.getUsagePackageById(id), HttpStatus.OK);
    }

    @GetMapping("/get/current-usage-package/{userId}")
    public ResponseEntity<ApiResponse<?>> getCurrentUsagePackageByUserId(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(this.usagePackageService.getCurrentUsagePackageByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/get/usage-package-by-user/{userId}")
    public ResponseEntity<ApiResponse<?>> getUsagePackageByUserId(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(this.usagePackageService.getUsagePackageByUserId(userId), HttpStatus.OK);
    }
}
