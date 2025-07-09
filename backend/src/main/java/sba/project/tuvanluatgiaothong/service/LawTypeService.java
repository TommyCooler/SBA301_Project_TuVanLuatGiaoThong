package sba.project.tuvanluatgiaothong.service;

import org.springframework.stereotype.Service;
import sba.project.tuvanluatgiaothong.dto.LawTypeResponseDTO;
import sba.project.tuvanluatgiaothong.pojo.LawType;
import sba.project.tuvanluatgiaothong.repository.LawTypeRepository;

import java.util.List;

@Service
public class LawTypeService implements ILawTypeService{

    private final LawTypeRepository lawTypeRepository;

    public LawTypeService(LawTypeRepository lawTypeRepository) {
        this.lawTypeRepository = lawTypeRepository;
    }
    @Override
    public LawTypeResponseDTO getLawTypeById(int id) {
        return null;
    }

    @Override
    public List<LawTypeResponseDTO> getAllLawTypes() {
        List<LawType> lawTypes = lawTypeRepository.findAll();
        List<LawTypeResponseDTO> lawTypeResponses = lawTypes.stream()
                .map(this::convertToLawType)
                .toList();
        return lawTypeResponses;
    }

    @Override
    public LawTypeResponseDTO createLawType(LawTypeResponseDTO lawTypeResponseDTO) {
        return null;
    }

    @Override
    public LawTypeResponseDTO updateLawType(int id, LawTypeResponseDTO lawTypeResponseDTO) {
        return null;
    }

    @Override
    public void deleteLawType(int id) {

    }

    private LawTypeResponseDTO convertToLawType(LawType lawType) {
        LawTypeResponseDTO lawTypeResponse = new LawTypeResponseDTO();
        lawTypeResponse.setId(lawType.getId());
        lawTypeResponse.setName(lawType.getName());
        lawTypeResponse.setDeleted(false); // Assuming you want to set deleted as false for the response
        lawTypeResponse.setCreatedDate(lawType.getCreatedDate());
        lawTypeResponse.setUpdatedDate(lawType.getUpdatedDate());
        return lawTypeResponse;
    }
}
