//package sba.project.tuvanluatgiaothong.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import sba.project.tuvanluatgiaothong.enums.UserPackageStatus;
//import sba.project.tuvanluatgiaothong.pojo.UserPackage;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.UUID;
//
//@Repository
//public interface UserPackageRepository extends JpaRepository<UserPackage, Long> {
//    List<UserPackage> findByUserIdAndStatus(UUID userId, UserPackageStatus status);
//
//    List<UserPackage> findByUserId(UUID userId);
//
//    List<UserPackage> findByExpirationDateBeforeAndStatus(LocalDateTime date, UserPackageStatus status);
//
//    @Query("SELECT up FROM UserPackage up JOIN up.usagePackage p WHERE LOWER(p.name) LIKE LOWER(concat('%', :name, '%'))")
//    List<UserPackage> findByPackageNameContaining(@Param("name") String name);
//}

package sba.project.tuvanluatgiaothong.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sba.project.tuvanluatgiaothong.pojo.UserPackage;

@Repository
public interface UserPackageRepository extends JpaRepository<UserPackage, UUID> {

    @Query("SELECT up FROM UserPackage up WHERE up.userId = ?1 AND up.isEnable = ?2")
    Optional<UserPackage> findByUserIdAndIsEnable(UUID userId, boolean enable);

    List<UserPackage> findByUserId(UUID userId);

    Optional<UserPackage> findByOrderId(String orderId);

    void deleteByUserId(UUID userId);

    @Query("SELECT up FROM UserPackage up WHERE up.isEnable = true AND up.expiredDate < :currentDate")
    List<UserPackage> findExpiredEnabledPackages(@Param("currentDate") Instant currentDate);
}
