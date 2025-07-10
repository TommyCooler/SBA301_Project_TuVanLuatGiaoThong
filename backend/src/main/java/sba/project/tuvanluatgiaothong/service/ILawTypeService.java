package sba.project.tuvanluatgiaothong.service;


import java.util.List;
import java.util.UUID;

import sba.project.tuvanluatgiaothong.dto.request.LawTypeRequest;
import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
import sba.project.tuvanluatgiaothong.dto.response.LawTypeResponse;

public interface ILawTypeService {

    public ApiResponse<LawTypeResponse> createLawType(LawTypeRequest lawTypeRequest);
    public ApiResponse<List<LawTypeResponse>> getAllLawTypes();
    public ApiResponse<LawTypeResponse> updateLawTypes(UUID id, LawTypeRequest lawTypeRequest);
    public ApiResponse<LawTypeResponse> deactivateLawType(UUID id);
    public ApiResponse<LawTypeResponse> getLawTypeById(UUID id);

}
