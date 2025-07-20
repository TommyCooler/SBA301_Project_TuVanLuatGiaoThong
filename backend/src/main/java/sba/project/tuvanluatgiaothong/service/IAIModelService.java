package sba.project.tuvanluatgiaothong.service;

import sba.project.tuvanluatgiaothong.dto.request.AIModelRequest;
import sba.project.tuvanluatgiaothong.dto.response.AIModelResponse;
import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;

import java.util.List;

public interface IAIModelService {

    public ApiResponse<AIModelResponse> createAIModel(AIModelRequest aiModelRequest);

    public ApiResponse<List<AIModelResponse>> getAllAIModel();
}
