//package sba.project.tuvanluatgiaothong.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import sba.project.tuvanluatgiaothong.dto.request.PurchasePackageRequestDTO;
//import sba.project.tuvanluatgiaothong.dto.response.UserPackageDTO;
//import sba.project.tuvanluatgiaothong.enums.UserPackageStatus;
//import sba.project.tuvanluatgiaothong.exception.CustomExceptions.ResourceNotFoundException;
//import sba.project.tuvanluatgiaothong.pojo.User;
//import sba.project.tuvanluatgiaothong.pojo.UsagePackage;
//import sba.project.tuvanluatgiaothong.pojo.UserPackage;
//import sba.project.tuvanluatgiaothong.repository.UserPackageRepository;
//import sba.project.tuvanluatgiaothong.repository.UserRepository;
//import sba.project.tuvanluatgiaothong.repository.UsagePackageRepository;
//
//import java.util.UUID;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class UserPackageService implements IUserPackageService {
//    private final UserPackageRepository userPackageRepository;
//    private final UserRepository userRepository;
//    private final UsagePackageRepository usagePackageRepository;
//
//    @Override
//    @Transactional
//    public UserPackageDTO recordPurchase(PurchasePackageRequestDTO request) {
//        User user = userRepository.findById(request.getUserId())
//                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//
//        UsagePackage usagePackage = usagePackageRepository.findById(request.getUsagePackageId())
//                .orElseThrow(() -> new ResourceNotFoundException("Package not found"));
//
//        UserPackage userPackage = new UserPackage();
//        userPackage.setUser(user);
//        userPackage.setUsagePackage(usagePackage);
//        userPackage.setTransactionDate(LocalDateTime.now());
//        userPackage.setTransactionMethod(request.getTransactionMethod());
//        userPackage.setStatus(UserPackageStatus.ACTIVE);
//        userPackage.setExpirationDate(LocalDateTime.now().plusDays(usagePackage.getDaysLimit()));
//        userPackage.setCreatedDate(LocalDateTime.now());
//        userPackage.setUpdatedDate(LocalDateTime.now());
//
//        return convertToDTO(userPackageRepository.save(userPackage));
//    }
//
//    @Override
//    @Transactional
//    public void blockUserPackage(Long userPackageId) {
//        UserPackage userPackage = userPackageRepository.findById(userPackageId)
//                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));
//        userPackage.setStatus(UserPackageStatus.BLOCKED);
//        userPackage.setUpdatedDate(LocalDateTime.now());
//        userPackageRepository.save(userPackage);
//    }
//
//    @Override
//    @Transactional
//    public void unblockUserPackage(Long userPackageId) {
//        UserPackage userPackage = userPackageRepository.findById(userPackageId)
//                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));
//        userPackage.setStatus(UserPackageStatus.ACTIVE);
//        userPackage.setUpdatedDate(LocalDateTime.now());
//        userPackageRepository.save(userPackage);
//    }
//
//    @Override
//    public List<UserPackageDTO> getUserActiveSubscriptions(UUID userId) {
//        return userPackageRepository.findByUserIdAndStatus(userId, UserPackageStatus.ACTIVE)
//                .stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<UserPackageDTO> getUserSubscriptionHistory(UUID userId) {
//        return userPackageRepository.findByUserId(userId)
//                .stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<UserPackageDTO> getExpiredSubscriptions() {
//        return userPackageRepository
//                .findByExpirationDateBeforeAndStatus(LocalDateTime.now(), UserPackageStatus.ACTIVE)
//                .stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<UserPackageDTO> searchByPackageName(String packageName) {
//        return userPackageRepository.findByPackageNameContaining(packageName)
//                .stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<UserPackageDTO> findAllSubscription() {
//        return userPackageRepository.findAll()
//                .stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    private UserPackageDTO convertToDTO(UserPackage entity) {
//        UserPackageDTO dto = new UserPackageDTO();
//        dto.setUserPackageId(entity.getId());
//        dto.setUserId(entity.getUser().getId());
//        dto.setUserName(entity.getUser().getFullName());
//        dto.setUsagePackageId(entity.getUsagePackage().getUsagePackageId());
//        dto.setPackageName(entity.getUsagePackage().getName());
//        dto.setPackagePrice(entity.getUsagePackage().getPrice());
//        dto.setDailyLimit(entity.getUsagePackage().getDailyLimit());
//        dto.setDaysLimit(entity.getUsagePackage().getDaysLimit());
//        dto.setTransactionDate(entity.getTransactionDate());
//        dto.setTransactionMethod(entity.getTransactionMethod());
//        dto.setExpirationDate(entity.getExpirationDate());
//        dto.setStatus(entity.getStatus());
//        dto.setCreatedDate(entity.getCreatedDate());
//        dto.setUpdatedDate(entity.getUpdatedDate());
//        return dto;
//    }
//}

package sba.project.tuvanluatgiaothong.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sba.project.tuvanluatgiaothong.dto.request.UserPackageRequest;
import sba.project.tuvanluatgiaothong.dto.response.UserPackageResponse;
import sba.project.tuvanluatgiaothong.exception.CustomExceptions;
import sba.project.tuvanluatgiaothong.pojo.UserPackage;
import sba.project.tuvanluatgiaothong.repository.ITransactionUserPackage;
import sba.project.tuvanluatgiaothong.repository.UserPackageRepository;

@Service
@RequiredArgsConstructor
public class UserPackageServiceImpl implements IUserPackageService {

    private UserPackageRepository userPackageRepository;

    private ITransactionUserPackage transactionUserPackage;

    @Override
    public UserPackageResponse createUserPackage(UserPackageRequest requestDto) {
        UserPackage userPackage = new UserPackage();
        userPackage.setUserId(requestDto.getUserId());
        userPackage.setPackageId(requestDto.getPackageId());
        userPackage.setPrice(requestDto.getPrice());
        this.userPackageRepository.save(userPackage);
        return convertToResponseDto(userPackage);
    }

    @Override
    public UserPackageResponse getUserPackageById(UUID id) {
        UserPackage userPackage = this.userPackageRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("UserPackage not found with id: " + id));
        return convertToResponseDto(userPackage);
    }

    @Override
    public UserPackageResponse getUserPackageByUserId(UUID userId) {
        try {
            UserPackage currentUserPackage = this.userPackageRepository.findByUserIdAndIsEnable(userId, true)
                    .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("User package not found with user id: " + userId));
            return convertToResponseDto(currentUserPackage);
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Override
    public List<UserPackageResponse> getAllUserPackages() {
        List<UserPackage> userPackages = userPackageRepository.findAll();
        return userPackages.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserPackageResponse updateUserPackage(UUID id, UserPackageRequest requestDto) {
        UserPackage userPackage = this.userPackageRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("UserPackage not found with id: " + id));
        userPackage.setUserId(requestDto.getUserId());
        userPackage.setPackageId(requestDto.getPackageId());
        userPackage.setPrice(requestDto.getPrice());
        this.transactionUserPackage.save(userPackage);
        return convertToResponseDto(userPackage);
    }

    @Override
    public void deleteUserPackage(UUID id) {
        UserPackage userPackage = this.userPackageRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("UserPackage not found with id: " + id));
        try {
            this.transactionUserPackage.delete(id);
        }
        catch (Exception exception) {
            throw new CustomExceptions.InternalServerException("Delete user package fail with id: "+ id);
        }
    }

    private UserPackageResponse convertToResponseDto(UserPackage userPackage) {
        return UserPackageResponse.builder()
                .id(userPackage.getId())
                .userId(userPackage.getUserId())
                .packageId(userPackage.getPackageId())
                .price(userPackage.getPrice())
                .transactionDate(userPackage.getTransactionDate())
                .build();
    }

}
