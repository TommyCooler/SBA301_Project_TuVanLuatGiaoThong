package sba.project.tuvanluatgiaothong.mapper;

import org.springframework.stereotype.Component;
import sba.project.tuvanluatgiaothong.dto.request.UsagePackageRequest;
import sba.project.tuvanluatgiaothong.dto.response.UsagePackageResponse;
import sba.project.tuvanluatgiaothong.pojo.UsagePackage;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Component("usagePackageMapper_UsagePackageService")
public class UsagePackageMapper {

    public UsagePackage toEntity(UsagePackageRequest usagePackageRequest) {
        if (usagePackageRequest == null)
            return null;
        return UsagePackage.builder()
                .id(UUID.randomUUID())
                .name(usagePackageRequest.getName())
                .description(usagePackageRequest.getDescription())
                .price(usagePackageRequest.getPrice())
                .dailyLimit(usagePackageRequest.getDailyLimit())
                .daysLimit(usagePackageRequest.getDaysLimit())
                .isDeleted(false)
                .build();
    }

    public UsagePackageResponse toResponse(UsagePackage usagePackage) {
        if (usagePackage == null)
            return null;
        UsagePackageResponse response = new UsagePackageResponse();
        response.setId(usagePackage.getId());
        response.setName(usagePackage.getName());
        response.setDescription(usagePackage.getDescription());
        response.setPrice(usagePackage.getPrice());
        response.setDailyLimit(usagePackage.getDailyLimit());
        response.setDaysLimit(usagePackage.getDaysLimit());
        response.setModelType(usagePackage.getModelType().name());
        response.setDeleted(usagePackage.isDeleted());
        response.setCreatedDate(usagePackage.getCreatedDate());
        response.setUpdatedDate(usagePackage.getUpdatedDate());
        return response;
    }

    public UsagePackage copyDataWithoutId(UsagePackageRequest usagePackageRequest, UsagePackage usagePackage) {
        if (usagePackage == null) return null;
        if (usagePackageRequest == null) return usagePackage;
        var zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        var now = ZonedDateTime.now(zoneId).toInstant();

        usagePackage.setName(usagePackageRequest.getName());
        usagePackage.setDescription(usagePackageRequest.getDescription());
        usagePackage.setPrice(usagePackageRequest.getPrice());
        usagePackage.setDailyLimit(usagePackageRequest.getDailyLimit());
        usagePackage.setDaysLimit(usagePackageRequest.getDaysLimit());
        usagePackage.setUpdatedDate(now);
        return usagePackage;
    }

}