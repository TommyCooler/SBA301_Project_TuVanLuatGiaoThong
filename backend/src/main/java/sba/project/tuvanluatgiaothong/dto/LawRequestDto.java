package sba.project.tuvanluatgiaothong.dto;

import java.time.Instant;
import java.util.UUID;

import lombok.Data;

@Data
public class LawRequestDto {
    UUID id;
    String title; // Sửa lại từ 'tittle' thành 'title'
    String description;
    Instant issueDate;
    Instant effectiveDate;
    String sourceUrl;
    String filePath;
    boolean isDeleted;
    UUID lawTypeId;
}



