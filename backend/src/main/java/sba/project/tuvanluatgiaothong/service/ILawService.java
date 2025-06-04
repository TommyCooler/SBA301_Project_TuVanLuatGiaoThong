package sba.project.tuvanluatgiaothong.service;

import java.util.List;
import java.util.UUID;

import sba.project.tuvanluatgiaothong.dto.LawRequestDto;
import sba.project.tuvanluatgiaothong.dto.LawResponseDto;
import sba.project.tuvanluatgiaothong.pojo.Law;

public interface ILawService {

    LawResponseDto createLaw(LawRequestDto lawRequestDto);
    LawResponseDto getLawById(UUID id);
    List<LawResponseDto> getAllLaw();
    LawResponseDto update(UUID id, LawRequestDto lawRequestDto);
    void delete(UUID id);
}
