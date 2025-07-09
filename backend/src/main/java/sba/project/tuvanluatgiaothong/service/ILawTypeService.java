package sba.project.tuvanluatgiaothong.service;

import sba.project.tuvanluatgiaothong.dto.LawTypeResponseDTO;

import java.util.List;

public interface ILawTypeService {

    LawTypeResponseDTO getLawTypeById(int id);
    List<LawTypeResponseDTO> getAllLawTypes();
    LawTypeResponseDTO createLawType(LawTypeResponseDTO lawTypeResponseDTO);
    LawTypeResponseDTO updateLawType(int id, LawTypeResponseDTO lawTypeResponseDTO);
    void deleteLawType(int id);

}
