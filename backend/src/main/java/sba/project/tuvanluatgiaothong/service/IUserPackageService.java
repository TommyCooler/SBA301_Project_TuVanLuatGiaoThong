//package sba.project.tuvanluatgiaothong.service;
//
//import sba.project.tuvanluatgiaothong.dto.request.PurchasePackageRequestDTO;
//import sba.project.tuvanluatgiaothong.dto.response.UserPackageDTO;
//import java.util.List;
//import java.util.UUID;
//
//public interface IUserPackageService {
//    UserPackageDTO recordPurchase(PurchasePackageRequestDTO request);
//    void blockUserPackage(Long userPackageId);
//    void unblockUserPackage(Long userPackageId);
//    List<UserPackageDTO> getUserActiveSubscriptions(UUID userId);
//    List<UserPackageDTO> getUserSubscriptionHistory(UUID userId);
//    List<UserPackageDTO> getExpiredSubscriptions();
//    List<UserPackageDTO> searchByPackageName(String packageName);
//    List<UserPackageDTO> findAllSubscription();
//}

package sba.project.tuvanluatgiaothong.service;

import sba.project.tuvanluatgiaothong.dto.request.UserPackageRequest;
import sba.project.tuvanluatgiaothong.dto.response.UserPackageResponse;

import java.util.List;
import java.util.UUID;

public interface IUserPackageService {
    UserPackageResponse createUserPackage(UserPackageRequest userPackageRequest);
    UserPackageResponse getUserPackageById(UUID id);
    UserPackageResponse getUserPackageByUserId(UUID userId);
    List<UserPackageResponse> getAllUserPackages();
    UserPackageResponse updateUserPackage(UUID id, UserPackageRequest userPackageRequest);
    void deleteUserPackage(UUID id);
}
