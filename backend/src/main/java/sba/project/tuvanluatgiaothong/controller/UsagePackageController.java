package sba.project.tuvanluatgiaothong.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sba.project.tuvanluatgiaothong.dto.request.CreateUsagePackageDTO;
import sba.project.tuvanluatgiaothong.dto.response.UsagePackageDTO;
import sba.project.tuvanluatgiaothong.service.IUsagePackageService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/packages")
@RequiredArgsConstructor
public class UsagePackageController {
    private final IUsagePackageService usagePackageService;

    @GetMapping
    public ResponseEntity<List<UsagePackageDTO>> getAllPackages() {
        return ResponseEntity.ok(usagePackageService.getAllPackages());
    }

    @GetMapping("/active")
    public ResponseEntity<List<UsagePackageDTO>> getAllActivePackages() {
        return ResponseEntity.ok(usagePackageService.getAllActivePackages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsagePackageDTO> getPackageById(@PathVariable UUID id) {
        return ResponseEntity.ok(usagePackageService.getPackageById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<UsagePackageDTO>> searchPackages(@RequestParam String keyword) {
        return ResponseEntity.ok(usagePackageService.searchPackages(keyword));
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<UsagePackageDTO>> getPackagesWithinPrice(
            @RequestParam(required = false) Float minPrice,
            @RequestParam(required = false) Float maxPrice) {
        return ResponseEntity.ok(usagePackageService.getPackagesWithinPrice(minPrice, maxPrice));
    }

    @PostMapping
    public ResponseEntity<UsagePackageDTO> createPackage(@RequestBody CreateUsagePackageDTO dto) {
        return ResponseEntity.ok(usagePackageService.createPackage(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsagePackageDTO> updatePackage(
            @PathVariable UUID id,
            @RequestBody CreateUsagePackageDTO dto) {
        return ResponseEntity.ok(usagePackageService.updatePackage(id, dto));
    }

    @PatchMapping("/{id}/disable")
    public ResponseEntity<Void> disablePackage(@PathVariable UUID id) {
        usagePackageService.disablePackage(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/enable")
    public ResponseEntity<Void> enablePackage(@PathVariable UUID id) {
        usagePackageService.enablePackage(id);
        return ResponseEntity.ok().build();
    }
}