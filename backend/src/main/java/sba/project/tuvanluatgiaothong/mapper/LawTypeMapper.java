package sba.project.tuvanluatgiaothong.mapper;

import org.springframework.stereotype.Component;

import sba.project.tuvanluatgiaothong.dto.request.LawTypeRequest;
import sba.project.tuvanluatgiaothong.dto.response.LawTypeResponse;
import sba.project.tuvanluatgiaothong.pojo.LawType;

import java.util.UUID;

@Component("lawTypeMapper_LawService")
public class LawTypeMapper {

    public LawType toEntity(LawTypeRequest lawTypeRequest) {
        if (lawTypeRequest == null) return null;
        return LawType.builder()
                .id(UUID.randomUUID())
                .name(lawTypeRequest.getName())
                .isDeleted(Boolean.FALSE)
                .build();
    }

    public LawTypeResponse toResponse(LawType lawType) {
        if (lawType == null)
            return null;
        LawTypeResponse response = new LawTypeResponse();
        response.setId(lawType.getId());
        response.setName(lawType.getName());
        response.setDeleted(lawType.isDeleted());
        response.setCreatedDate(lawType.getCreatedDate());
        response.setUpdatedDate(lawType.getUpdatedDate());
        return response;
    }

    public LawType copyWithoutId(LawTypeRequest lawTypeRequest, LawType lawType) {
        if (lawTypeRequest == null || lawType == null) {
            return null;
        }
        lawType.setName(lawType.getName());
        return lawType;
    }

}
