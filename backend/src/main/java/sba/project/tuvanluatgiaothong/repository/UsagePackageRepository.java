//package sba.project.tuvanluatgiaothong.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import sba.project.tuvanluatgiaothong.pojo.UsagePackage;
//
//import java.util.List;
//import java.util.UUID;
//
//@Repository
//public interface UsagePackageRepository extends JpaRepository<UsagePackage, UUID> {
//    List<UsagePackage> findByIsEnableTrue();
//
//    List<UsagePackage> findByNameContainingIgnoreCase(String name);
//    @Query("SELECT u FROM UsagePackage u WHERE " +
//            "(:minPrice IS NULL OR u.price >= :minPrice) AND " +
//            "(:maxPrice IS NULL OR u.price <= :maxPrice)")
//    List<UsagePackage> findByPriceBetween(
//            @Param("minPrice") Float minPrice,
//            @Param("maxPrice") Float maxPrice
//    );
//}

package sba.project.tuvanluatgiaothong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sba.project.tuvanluatgiaothong.pojo.UsagePackage;

import java.util.UUID;

@Repository
public interface UsagePackageRepository extends JpaRepository<UsagePackage, UUID> {

    @Query("SELECT u.daysLimit FROM UsagePackage u WHERE u.id = ?1")
    Integer getDayLimitOfUsagePackageById(UUID id);

}
