package sba.project.tuvanluatgiaothong.service;

import sba.project.tuvanluatgiaothong.dto.response.UsagePackageResponse;
import sba.project.tuvanluatgiaothong.dto.request.CreateUsagePackageDTO;
import sba.project.tuvanluatgiaothong.dto.request.UsagePackageRequest;
import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
import sba.project.tuvanluatgiaothong.dto.response.UsagePackageDTO;

import java.util.List;
import java.util.UUID;

public interface IUsagePackageService {
//    List<UsagePackageDTO> getAllPackages();
//    List<UsagePackageDTO> getAllActivePackages();
//    UsagePackageDTO getPackageById(UUID id);
//    List<UsagePackageDTO> searchPackages(String keyword);
//    List<UsagePackageDTO> getPackagesWithinPrice(Float minPrice, Float maxPrice);
//    UsagePackageDTO createPackage(CreateUsagePackageDTO dto);
//    UsagePackageDTO updatePackage(UUID id, CreateUsagePackageDTO dto);
//    void disablePackage(UUID id);
//    void enablePackage(UUID id);

    public ApiResponse<UsagePackageResponse> createUsagePackage(UsagePackageRequest usagePackageRequest);

    public ApiResponse<UsagePackageResponse> updateUsagePackage(UUID id, UsagePackageRequest usagePackageRequest);

    public ApiResponse<List<UsagePackageResponse>> getAllUsagePackage();

    public ApiResponse<UsagePackageResponse> getUsagePackageById(UUID id);

    public ApiResponse<UsagePackageResponse> deactivateUsagePackage(UUID id);

    public ApiResponse<UsagePackageResponse> getCurrentUsagePackageByUserId(String userId);
}