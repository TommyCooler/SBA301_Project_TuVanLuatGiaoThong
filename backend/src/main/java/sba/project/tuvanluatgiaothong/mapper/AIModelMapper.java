package sba.project.tuvanluatgiaothong.mapper;

import org.springframework.stereotype.Component;
import sba.project.tuvanluatgiaothong.dto.request.AIModelRequest;
import sba.project.tuvanluatgiaothong.dto.response.AIModelResponse;
import sba.project.tuvanluatgiaothong.pojo.AIModel;

import java.util.UUID;

@Component("aiModelMapper_UserPackageService")
public class AIModelMapper {

    public AIModel toEntity(AIModelRequest aiModelRequest) {
        return AIModel.builder()
                .id(UUID.randomUUID())
                .modelName(aiModelRequest.getModelName())
                .provider(aiModelRequest.getProvider())
                .description(aiModelRequest.getDescription())
                .isDeleted(Boolean.FALSE)
                .build();
    }

    public AIModelResponse toResponse(AIModel aiModel) {
        return AIModelResponse.
                builder()
                .id(aiModel.getId())
                .modelName(aiModel.getModelName())
                .provider(aiModel.getProvider())
                .description(aiModel.getDescription())
                .modelAlias(aiModel.getAlias())
                .isDeleted(aiModel.isDeleted())
                .createdDate(aiModel.getCreatedDate())
                .updatedDate(aiModel.getUpdatedDate())
                .build();
    }

}
