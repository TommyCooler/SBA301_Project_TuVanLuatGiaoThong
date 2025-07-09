package sba.project.tuvanluatgiaothong.service;

import java.util.List;
import java.util.UUID;

import sba.project.tuvanluatgiaothong.dto.request.LawRequestDto;
import sba.project.tuvanluatgiaothong.dto.LawResponseDto;

public interface ILawService {

    LawResponseDto createLaw(LawRequestDto lawRequestDto);
    LawResponseDto getLawById(UUID id);
    List<LawResponseDto> getAllLaw();
    LawResponseDto update(UUID id, LawRequestDto lawRequestDto);
    void delete(UUID id);
}
