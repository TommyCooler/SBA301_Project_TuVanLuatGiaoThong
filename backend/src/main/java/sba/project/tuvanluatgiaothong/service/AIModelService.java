package sba.project.tuvanluatgiaothong.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sba.project.tuvanluatgiaothong.dto.request.AIModelRequest;
import sba.project.tuvanluatgiaothong.dto.response.AIModelResponse;
import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
import sba.project.tuvanluatgiaothong.exception.CustomExceptions;
import sba.project.tuvanluatgiaothong.mapper.AIModelMapper;
import sba.project.tuvanluatgiaothong.repository.AIModelRepository;
import sba.project.tuvanluatgiaothong.repository.IAIModelTransaction;

import java.util.List;

@Service
@AllArgsConstructor
public class AIModelService implements IAIModelService {

    private final IAIModelTransaction transactionAIModel;

    private final AIModelMapper aiModelMapper;

    private final AIModelRepository aiModelRepository;

    @Override
    public ApiResponse<AIModelResponse> createAIModel(AIModelRequest aiModelRequest) {
        try {
            var savedAIModel = this.transactionAIModel.save(this.aiModelMapper.toEntity(aiModelRequest));
            return ApiResponse.<AIModelResponse>builder()
                    .status("success")
                    .message("Create AI model successfully")
                    .dataResponse(this.aiModelMapper.toResponse(savedAIModel))
                    .build();

        }
        catch (Exception ex) {
            throw new CustomExceptions.InternalServerException("Failed to create AI model: " + ex.getMessage());
        }
    }

    @Override
    public ApiResponse<List<AIModelResponse>> getAllAIModel() {
        try {
            var aimModels = this.aiModelRepository.findAll().stream().map(
                    this.aiModelMapper::toResponse
            ).toList();
            return ApiResponse.<List<AIModelResponse>>builder()
                    .status("success")
                    .dataResponse(aimModels)
                    .build();
        }
        catch (Exception ex) {
            throw new CustomExceptions.InternalServerException("Failed to get AI models: " + ex.getMessage());
        }
    }
}
