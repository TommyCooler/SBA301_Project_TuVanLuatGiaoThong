package sba.project.tuvanluatgiaothong.service;

import lombok.RequiredArgsConstructor;
import sba.project.tuvanluatgiaothong.dto.request.LawTypeRequest;
import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
import sba.project.tuvanluatgiaothong.dto.response.LawTypeResponse;
import sba.project.tuvanluatgiaothong.exception.CustomExceptions;
import sba.project.tuvanluatgiaothong.mapper.LawTypeMapper;
import sba.project.tuvanluatgiaothong.repository.ILawTypeTransaction;
import sba.project.tuvanluatgiaothong.repository.LawTypeRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LawTypeService implements ILawTypeService {

    private final LawTypeRepository lawTypeRepository;

    private final ILawTypeTransaction transactionLawType;

    private final LawTypeMapper lawTypeMapper;

    @Override
    public ApiResponse<LawTypeResponse> createLawType(LawTypeRequest lawTypeRequest) {
        try {
            var savedLawType = this.transactionLawType.save(
                    this.lawTypeMapper.toEntity(lawTypeRequest)
            );
            return ApiResponse.<LawTypeResponse>builder()
                    .status("success")
                    .message("Create law type successfully!")
                    .dataResponse(this.lawTypeMapper.toResponse(savedLawType))
                    .build();
        }
        catch (Exception exception) {
            throw new CustomExceptions.InternalServerException(exception.getMessage());
        }
    }

    @Override
    public ApiResponse<List<LawTypeResponse>> getAllLawTypes() {
        try {
            var lawTypes = this.lawTypeRepository.findAll().stream().map(
                    this.lawTypeMapper::toResponse
            ).toList();
            return ApiResponse.<List<LawTypeResponse>>builder()
                    .status("success")
                    .message("Get all law type successfully!")
                    .dataResponse(lawTypes)
                    .build();
        }
        catch (Exception exception) {
            throw new CustomExceptions.InternalServerException(exception.getMessage());
        }
    }

    @Override
    public ApiResponse<LawTypeResponse> updateLawTypes(UUID id, LawTypeRequest lawTypeRequest) {
        var law = this.lawTypeRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Law type with id "+id+" not found!"));
        try {
            law = this.lawTypeMapper.copyWithoutId(lawTypeRequest, law);
            var updatedLaw = this.transactionLawType.save(law);
            return ApiResponse.<LawTypeResponse>builder()
                    .status("success")
                    .message("Update law type successfully!")
                    .dataResponse(this.lawTypeMapper.toResponse(updatedLaw))
                    .build();
        }
        catch (Exception exception) {
            throw new CustomExceptions.InternalServerException(exception.getMessage());
        }
    }

    @Override
    public ApiResponse<LawTypeResponse> deactivateLawType(UUID id) {
        var law = this.lawTypeRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Law type with id "+id+" not found!"));
        try {
            law.setDeleted(Boolean.TRUE);
            var updatedLaw = this.transactionLawType.save(law);
            return ApiResponse.<LawTypeResponse>builder()
                    .status("success")
                    .message("Deactivate law type successfully!")
                    .dataResponse(this.lawTypeMapper.toResponse(updatedLaw))
                    .build();
        }
        catch (Exception exception) {
            throw new CustomExceptions.InternalServerException(exception.getMessage());
        }
    }

    @Override
    public ApiResponse<LawTypeResponse> getLawTypeById(UUID id) {
        var law = this.lawTypeRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Law type with id "+id+" not found!"));
        return ApiResponse.<LawTypeResponse>builder()
                .status("success")
                .message("Get law type with id "+id+" successfully!")
                .dataResponse(this.lawTypeMapper.toResponse(law))
                .build();
    }
}
