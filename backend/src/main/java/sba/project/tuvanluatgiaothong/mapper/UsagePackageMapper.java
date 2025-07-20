package sba.project.tuvanluatgiaothong.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sba.project.tuvanluatgiaothong.dto.request.UsagePackageRequest;
import sba.project.tuvanluatgiaothong.dto.response.UsagePackageResponse;
import sba.project.tuvanluatgiaothong.exception.CustomExceptions;
import sba.project.tuvanluatgiaothong.pojo.AIModel;
import sba.project.tuvanluatgiaothong.pojo.UsagePackage;
import sba.project.tuvanluatgiaothong.repository.AIModelRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Component("usagePackageMapper_UsagePackageService")
@RequiredArgsConstructor
public class UsagePackageMapper {

    private final AIModelMapper aiModelMapper;

    private final AIModelRepository iAIModelRepository;

    public UsagePackage toEntity(UsagePackageRequest usagePackageRequest) {
        if (usagePackageRequest == null)
            return null;
        List<AIModel> aiModels = usagePackageRequest.getAiModels()
                .stream().map(aiModelRequest -> this.iAIModelRepository.findById(aiModelRequest.getId())
                        .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException(
                                "Cannot found AI model with id: " + aiModelRequest.getId())))
                .toList();

        return UsagePackage.builder()
                .id(UUID.randomUUID())
                .name(usagePackageRequest.getName())
                .description(usagePackageRequest.getDescription())
                .price(usagePackageRequest.getPrice())
                .dailyLimit(usagePackageRequest.getDailyLimit())
                .daysLimit(usagePackageRequest.getDaysLimit())
                .isDeleted(false)
                .aiModels(aiModels)
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
        response.setDeleted(usagePackage.isDeleted());
        response.setCreatedDate(usagePackage.getCreatedDate());
        response.setUpdatedDate(usagePackage.getUpdatedDate());
        response.setAiModels(
                usagePackage.getAiModels().stream().map(this.aiModelMapper::toResponse).toList()
        );
        return response;
    }

    public UsagePackage copyDataWithoutId(UsagePackageRequest usagePackageRequest, UsagePackage usagePackage) {
        if (usagePackage == null) return null;
        if (usagePackageRequest == null) return usagePackage;
        var zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        var now = ZonedDateTime.now(zoneId).toInstant();
        usagePackage.setId(usagePackage.getId());
        usagePackage.setName(usagePackageRequest.getName());
        usagePackage.setDescription(usagePackageRequest.getDescription());
        usagePackage.setPrice(usagePackageRequest.getPrice());
        usagePackage.setDailyLimit(usagePackageRequest.getDailyLimit());
        usagePackage.setDaysLimit(usagePackageRequest.getDaysLimit());
        usagePackage.setUpdatedDate(now);
        return usagePackage;
    }

}