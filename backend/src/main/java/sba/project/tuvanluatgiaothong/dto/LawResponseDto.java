package sba.project.tuvanluatgiaothong.dto;

import java.sql.Timestamp;
import java.util.UUID;
import lombok.Data;

@Data
public class LawResponseDto {
    UUID id;
    String tittle;
    Timestamp issueDate;
    Timestamp effectiveDate;
    String referenceNumber;
    String dateline;
    String sourceUrl;
    String filePath;
    boolean isDeleted;
    LawTypeResponseDTO lawType;
    Timestamp createdDate;
    Timestamp updatedDate;
}
