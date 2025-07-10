package sba.project.tuvanluatgiaothong.service;

import java.util.List;
import java.util.UUID;

import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
import sba.project.tuvanluatgiaothong.dto.response.LawResponse;
import sba.project.tuvanluatgiaothong.dto.request.LawRequest;

public interface ILawService {

    public ApiResponse<LawResponse> createLaw(LawRequest lawRequest);
    public ApiResponse<LawResponse> deactivateLaw(UUID id);
    public ApiResponse<List<LawResponse>> getAllLaws();
    public ApiResponse<LawResponse> updateLaw(UUID id, LawRequest lawRequest);
    public ApiResponse<LawResponse> getLawById(UUID id);
    List<LawResponse> getAllLaw();
    LawResponse update(UUID id, LawRequest lawRequest);
    void delete(UUID id);
    
}
