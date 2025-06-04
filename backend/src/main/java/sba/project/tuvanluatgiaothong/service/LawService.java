package sba.project.tuvanluatgiaothong.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sba.project.tuvanluatgiaothong.dto.LawRequestDto;
import sba.project.tuvanluatgiaothong.dto.LawResponseDto;
import sba.project.tuvanluatgiaothong.dto.LawTypeResponseDTO;
import sba.project.tuvanluatgiaothong.pojo.Law;
import sba.project.tuvanluatgiaothong.pojo.LawType;
import sba.project.tuvanluatgiaothong.repository.LawRepository;
import sba.project.tuvanluatgiaothong.repository.LawTypeRepository;

@Service
public class LawService implements ILawService {


    @Autowired
    private LawRepository lawRepository;

    @Autowired
    private LawTypeRepository lawTypeRepository;


    @Override
    public void delete(UUID id) {
        // Implementation logic for deleting a law by ID
        Law law = lawRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Law not found with id: " + id));
        law.setDeleted(true); // Assuming you want to mark it as deleted
        lawRepository.save(law); // Save the law to set it as deleted

    }

    @Override
    public LawResponseDto createLaw(LawRequestDto lawRequestDto) {
        // TODO Auto-generated method stub

        LawType lawType = lawTypeRepository.findById(lawRequestDto.getLawTypeId())
            .orElseThrow(() -> new IllegalArgumentException("LawType not found with id: " + lawRequestDto.getLawTypeId()));
        Law law = Law.builder()
        .title(lawRequestDto.getTittle())
        .lawType(lawType)
        .issueDate(lawRequestDto.getIssueDate())
        .effectiveDate(lawRequestDto.getEffectiveDate())
        .sourceUrl(lawRequestDto.getSourceUrl())
        .filePath(lawRequestDto.getFilePath())
        .isDeleted(lawRequestDto.isDeleted())
        .build();

        lawRepository.save(law);

        LawTypeResponseDTO lawTypeResponse = new LawTypeResponseDTO();
        lawTypeResponse.setId(lawType.getId());
        lawTypeResponse.setName(lawType.getName());
        lawTypeResponse.setDeleted(false); // Assuming you want to set deleted as false for the response
        lawTypeResponse.setCreatedDate(lawType.getCreatedDate());
        lawTypeResponse.setUpdatedDate(lawType.getUpdatedDate());

        LawResponseDto lawResponseDto = new LawResponseDto(); 
        lawResponseDto.setId(law.getId());
        lawResponseDto.setTittle(law.getTitle());
        lawResponseDto.setIssueDate(law.getIssueDate());
        lawResponseDto.setEffectiveDate(law.getEffectiveDate());
        lawResponseDto.setSourceUrl(law.getSourceUrl());
        lawResponseDto.setFilePath(law.getFilePath());
        lawResponseDto.setDeleted(law.isDeleted());
        lawResponseDto.setCreatedDate(law.getCreatedDate());
        lawResponseDto.setUpdatedDate(law.getUpdatedDate());
        lawResponseDto.setLawType(lawTypeResponse);
        return lawResponseDto;
        // throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public LawResponseDto update(UUID id, LawRequestDto lawRequestDto) {
       LawType lawType = lawTypeRepository.findById(lawRequestDto.getLawTypeId())
            .orElseThrow(() -> new IllegalArgumentException("LawType not found with id: " + lawRequestDto.getLawTypeId()));
        Law law = Law.builder()
        .title(lawRequestDto.getTittle())
        .lawType(lawType)
        .issueDate(lawRequestDto.getIssueDate())
        .effectiveDate(lawRequestDto.getEffectiveDate())
        .sourceUrl(lawRequestDto.getSourceUrl())
        .filePath(lawRequestDto.getFilePath())
        .isDeleted(lawRequestDto.isDeleted())
        .build();

        lawRepository.save(law);

        LawTypeResponseDTO lawTypeResponse = new LawTypeResponseDTO();
        lawTypeResponse.setId(lawType.getId());
        lawTypeResponse.setName(lawType.getName());
        lawTypeResponse.setDeleted(false); // Assuming you want to set deleted as false for the response
        lawTypeResponse.setCreatedDate(lawType.getCreatedDate());
        lawTypeResponse.setUpdatedDate(lawType.getUpdatedDate());

        LawResponseDto lawResponseDto = new LawResponseDto(); 
        lawResponseDto.setId(law.getId());
        lawResponseDto.setTittle(law.getTitle());
        lawResponseDto.setIssueDate(law.getIssueDate());
        lawResponseDto.setEffectiveDate(law.getEffectiveDate());
        lawResponseDto.setSourceUrl(law.getSourceUrl());
        lawResponseDto.setFilePath(law.getFilePath());
        lawResponseDto.setDeleted(law.isDeleted());
        lawResponseDto.setCreatedDate(law.getCreatedDate());
        lawResponseDto.setUpdatedDate(law.getUpdatedDate());
        lawResponseDto.setLawType(lawTypeResponse);
        return lawResponseDto;
    }

    @Override
    public LawResponseDto getLawById(UUID id) {
        // TODO Auto-generated method stub
        Law law = lawRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Law not found with id: " + id));
        LawType lawType = law.getLawType();

        LawTypeResponseDTO lawTypeResponse = new LawTypeResponseDTO();
        lawTypeResponse.setId(lawType.getId());
        lawTypeResponse.setName(lawType.getName());
        lawTypeResponse.setDeleted(false); // Assuming you want to set deleted as false for the response
        lawTypeResponse.setCreatedDate(lawType.getCreatedDate());
        lawTypeResponse.setUpdatedDate(lawType.getUpdatedDate());

        LawResponseDto lawResponseDto = new LawResponseDto();
        lawResponseDto.setId(law.getId());
        lawResponseDto.setTittle(law.getTitle());
        lawResponseDto.setIssueDate(law.getIssueDate());
        lawResponseDto.setEffectiveDate(law.getEffectiveDate());
        lawResponseDto.setSourceUrl(law.getSourceUrl());
        lawResponseDto.setFilePath(law.getFilePath());
        lawResponseDto.setDeleted(law.isDeleted());
        lawResponseDto.setLawType(lawTypeResponse);
        lawResponseDto.setCreatedDate(law.getCreatedDate());
        lawResponseDto.setUpdatedDate(law.getUpdatedDate());
        return lawResponseDto;
        // throw new UnsupportedOperationException("Unimplemented method 'read'");
    }

    @Override
    public List<LawResponseDto> getAllLaw() {
        // TODO Auto-generated method stub
        List<Law> laws = lawRepository.findAll();
        List<LawResponseDto> lawResponseDtos = laws.stream().map(law -> {
            LawResponseDto lawResponseDto = new LawResponseDto();
            lawResponseDto.setId(law.getId());
            lawResponseDto.setTittle(law.getTitle());
            lawResponseDto.setIssueDate(law.getIssueDate());
            lawResponseDto.setEffectiveDate(law.getEffectiveDate());
            lawResponseDto.setSourceUrl(law.getSourceUrl());
            lawResponseDto.setFilePath(law.getFilePath());
            lawResponseDto.setDeleted(law.isDeleted());
            lawResponseDto.setCreatedDate(law.getCreatedDate());
            lawResponseDto.setUpdatedDate(law.getUpdatedDate());
            return lawResponseDto;
        }).toList();
        throw new UnsupportedOperationException("Unimplemented method 'readAll'");
    }

   
    

   
}
