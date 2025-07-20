//package sba.project.tuvanluatgiaothong.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import sba.project.tuvanluatgiaothong.dto.request.CreateUsagePackageDTO;
//import sba.project.tuvanluatgiaothong.dto.response.UsagePackageDTO;
//import sba.project.tuvanluatgiaothong.exception.CustomExceptions.ResourceNotFoundException;
//import sba.project.tuvanluatgiaothong.pojo.UsagePackage;
//import sba.project.tuvanluatgiaothong.repository.UsagePackageRepository;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class UsagePackageService implements IUsagePackageService {
//    private final UsagePackageRepository usagePackageRepository;
//
//    @Override
//    public List<UsagePackageDTO> getAllPackages() {
//        return usagePackageRepository.findAll().stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<UsagePackageDTO> getAllActivePackages() {
//        return usagePackageRepository.findByIsEnableTrue().stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public UsagePackageDTO getPackageById(UUID id) {
//        return usagePackageRepository.findById(id)
//                .map(this::convertToDTO)
//                .orElseThrow(() -> new ResourceNotFoundException("Package not found"));
//    }
//
//    @Override
//    public List<UsagePackageDTO> searchPackages(String keyword) {
//        return usagePackageRepository.findByNameContainingIgnoreCase(keyword).stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<UsagePackageDTO> getPackagesWithinPrice(Float minPrice, Float maxPrice) {
//        if (minPrice == null && maxPrice == null) {
//            return getAllPackages();
//        }
//
//        float effectiveMinPrice = minPrice != null ? minPrice : Float.MIN_VALUE;
//        float effectiveMaxPrice = maxPrice != null ? maxPrice : Float.MAX_VALUE;
//
//        return usagePackageRepository.findByPriceBetween(effectiveMinPrice, effectiveMaxPrice).stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    @Transactional
//    public UsagePackageDTO createPackage(CreateUsagePackageDTO dto) {
//        UsagePackage usagePackage = UsagePackage.builder()
//                .usagePackageId(UUID.randomUUID())
//                .name(dto.getName())
//                .description(dto.getDescription())
//                .price(dto.getPrice())
//                .dailyLimit(dto.getDailyLimit())
//                .daysLimit(dto.getDaysLimit())
//                .isEnable(true)
//                .createdDate(LocalDateTime.now())
//                .updateDate(LocalDateTime.now())
//                .build();
//        return convertToDTO(usagePackageRepository.save(usagePackage));
//    }
//
//    @Override
//    @Transactional
//    public UsagePackageDTO updatePackage(UUID id, CreateUsagePackageDTO dto) {
//        UsagePackage existingPackage = usagePackageRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Package not found"));
//
//        if (dto.getName() != null) {
//            existingPackage.setName(dto.getName());
//        }
//        if (dto.getDescription() != null) {
//            existingPackage.setDescription(dto.getDescription());
//        }
//        if (dto.getPrice() != null) {
//            existingPackage.setPrice(dto.getPrice());
//        }
//        if (dto.getDailyLimit() != null) {
//            existingPackage.setDailyLimit(dto.getDailyLimit());
//        }
//        if (dto.getDaysLimit() != null) {
//            existingPackage.setDaysLimit(dto.getDaysLimit());
//        }
//
//        existingPackage.setUpdateDate(LocalDateTime.now());
//        return convertToDTO(usagePackageRepository.save(existingPackage));
//    }
//
//    @Override
//    @Transactional
//    public void disablePackage(UUID id) {
//        UsagePackage existingPackage = usagePackageRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Package not found"));
//        existingPackage.setIsEnable(false);
//        existingPackage.setUpdateDate(LocalDateTime.now());
//        usagePackageRepository.save(existingPackage);
//    }
//
//    @Override
//    @Transactional
//    public void enablePackage(UUID id) {
//        UsagePackage existingPackage = usagePackageRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Package not found"));
//        existingPackage.setIsEnable(true);
//        existingPackage.setUpdateDate(LocalDateTime.now());
//        usagePackageRepository.save(existingPackage);
//    }
//
//    private UsagePackageDTO convertToDTO(UsagePackage entity) {
//        UsagePackageDTO dto = new UsagePackageDTO();
//        dto.setId(entity.getUsagePackageId());
//        dto.setName(entity.getName());
//        dto.setDescription(entity.getDescription());
//        dto.setPrice(entity.getPrice());
//        dto.setDailyLimit(entity.getDailyLimit());
//        dto.setDaysLimit(entity.getDaysLimit());
//        dto.setIsEnable(entity.getIsEnable());
//        dto.setCreatedDate(entity.getCreatedDate());
//        dto.setUpdateDate(entity.getUpdateDate());
//        return dto;
//    }
//}

package sba.project.tuvanluatgiaothong.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sba.project.tuvanluatgiaothong.dto.response.UsagePackageResponse;
import sba.project.tuvanluatgiaothong.dto.request.UsagePackageRequest;
import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
import sba.project.tuvanluatgiaothong.exception.CustomExceptions;
import sba.project.tuvanluatgiaothong.mapper.UsagePackageMapper;
import sba.project.tuvanluatgiaothong.pojo.AIModel;
import sba.project.tuvanluatgiaothong.repository.AIModelRepository;
import sba.project.tuvanluatgiaothong.repository.IUsagePackageTransaction;
import sba.project.tuvanluatgiaothong.repository.UsagePackageRepository;
import sba.project.tuvanluatgiaothong.repository.UserPackageRepository;
import sba.project.tuvanluatgiaothong.utils.IHashingUtil;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsagePackageService implements IUsagePackageService {

    private final UsagePackageRepository usagePackageRepository;

    private final IUsagePackageTransaction transactionUsagePackage;

    private final UsagePackageMapper usagePackageMapper;

    private final UserPackageRepository userPackageRepository;

    private final AIModelRepository iAIModelRepository;

    private final IHashingUtil hashingUtil;

    @Override
    public ApiResponse<UsagePackageResponse> createUsagePackage(UsagePackageRequest usagePackageRequest) {
        try {
            var savedUsagePackage = this.transactionUsagePackage.save(
                    this.usagePackageMapper.toEntity(usagePackageRequest)
            );
            return ApiResponse.<UsagePackageResponse>builder()
                    .status("success")
                    .message("Create usage package successfully!")
                    .dataResponse(this.usagePackageMapper.toResponse(savedUsagePackage))
                    .build();
        }
        catch (Exception exception) {
            throw new CustomExceptions.InternalServerException("Create usage package fail, message: " + exception.getMessage());
        }
    }

    @Override
    public ApiResponse<UsagePackageResponse> updateUsagePackage(UUID id, UsagePackageRequest usagePackageRequest) {
        try {
            var usagePackage = this.usagePackageRepository.findById(id)
                    .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Cannot found usage package with id: "+ id));

            List<AIModel> aiModels = usagePackageRequest.getAiModels()
                    .stream().map(aiModelRequest -> this.iAIModelRepository.findById(aiModelRequest.getId())
                            .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException(
                                    "Cannot found AI model with id: " + aiModelRequest.getId())))
                    .toList();
            usagePackage = this.usagePackageMapper.copyDataWithoutId(usagePackageRequest, usagePackage);

            usagePackage.getAiModels().clear();
            for (AIModel aiModel : aiModels) {
                usagePackage.getAiModels().add(aiModel);
            }

            var updatedUsagePackage = this.transactionUsagePackage.save(usagePackage);
            return ApiResponse.<UsagePackageResponse>builder()
                    .status("success")
                    .message("Update usage package successfully!")
                    .dataResponse(this.usagePackageMapper.toResponse(updatedUsagePackage))
                    .build();
        }
        catch (Exception exception) {
            System.out.println("Exception occurred while updating usage package: " + exception.getMessage());
            throw new CustomExceptions.InternalServerException(
                    "Update usage package with id "+ id +" fail, message: " + exception.getMessage()
            );
        }
    }

    @Override
    public ApiResponse<List<UsagePackageResponse>> getAllUsagePackage() {
        try {
            var usagePackages = this.usagePackageRepository.findAll()
                    .stream().map(this.usagePackageMapper::toResponse).toList();
            return ApiResponse.<List<UsagePackageResponse>>builder()
                    .status("success")
                    .message("Get all usage packages successfully!")
                    .dataResponse(usagePackages)
                    .build();
        }
        catch (Exception exception) {
            throw new CustomExceptions.InternalServerException(
                    "Cannot get all usage packages, message: " + exception.getMessage()
            );
        }
    }

    @Override
    public ApiResponse<UsagePackageResponse> getUsagePackageById(UUID id) {
        try {
            var usagePackage = this.usagePackageRepository.findById(id)
                    .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Cannot found usage package with id: "+ id));
            return ApiResponse.<UsagePackageResponse>builder()
                    .status("success")
                    .message("Get usage package successfully!")
                    .dataResponse(this.usagePackageMapper.toResponse(usagePackage))
                    .build();
        }
        catch (Exception exception) {
            throw new CustomExceptions.InternalServerException(
                    "Update usage package with id "+ id +" fail, message: " + exception.getMessage()
            );
        }
    }

    @Override
    public ApiResponse<UsagePackageResponse> getUsagePackageByUserId(String userId) {
        UUID decodedUserId = UUID.fromString(this.hashingUtil.decode(userId));
        var currentUserPackage = this.userPackageRepository.findByUserIdAndIsEnable(decodedUserId, true)
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Cannot found user package with user id: " + decodedUserId));
        var usagePackage = this.usagePackageRepository.findById(currentUserPackage.getPackageId())
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Cannot found usage package with id: " + currentUserPackage.getPackageId()));
        return ApiResponse.<UsagePackageResponse>builder()
                .status("success")
                .message("Get usage package by user id successfully!")
                .dataResponse(this.usagePackageMapper.toResponse(usagePackage))
                .build();
    }

    @Override
    public ApiResponse<UsagePackageResponse> deactivateUsagePackage(UUID id) {
        try {
            var usagePackage = this.usagePackageRepository.findById(id)
                    .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Cannot found usage package with id: "+ id));
            usagePackage.setDeleted(Boolean.TRUE);
            var savedUsagePackage = this.transactionUsagePackage.save(usagePackage);
            return ApiResponse.<UsagePackageResponse>builder()
                    .status("success")
                    .message("Get usage package successfully!")
                    .dataResponse(this.usagePackageMapper.toResponse(savedUsagePackage))
                    .build();
        }
        catch (Exception exception) {
            throw new CustomExceptions.InternalServerException(
                    "Update usage package with id "+ id +" fail, message: " + exception.getMessage()
            );
        }
    }

    @Override
    public ApiResponse<UsagePackageResponse> getCurrentUsagePackageByUserId(String userId) {
        UUID decodedUserId = UUID.fromString(this.hashingUtil.decode(userId));
        var userPackageOptional = this.userPackageRepository.findByUserIdAndIsEnable(decodedUserId, true);
        UsagePackageResponse usagePackageResponse = null;

        if (userPackageOptional.isPresent()) {
            var userPackage = userPackageOptional.get();
            usagePackageResponse = this.usagePackageMapper.toResponse(
                    this.usagePackageRepository.findById(userPackage.getPackageId()).orElseThrow(
                            () -> new CustomExceptions.ResourceNotFoundException(
                                    "Cannot found usage package with id: " + userPackage.getPackageId())
                    )
            );
        }
        return ApiResponse.<UsagePackageResponse>builder()
                .status("success")
                .message("Get current usage package by user id successfully!")
                .dataResponse(usagePackageResponse)
                .build();
    }
}
