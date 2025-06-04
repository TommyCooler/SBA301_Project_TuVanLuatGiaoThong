package sba.project.tuvanluatgiaothong.dto;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;

@Data
public class LawResponseDto {
    UUID id;
    String tittle;
    String description;
    Timestamp issueDate;
    Timestamp effectiveDate;
    String sourceUrl;
    String filePath;
    boolean isDeleted;
    LawTypeResponseDTO lawType;
    Timestamp createdDate;
    Timestamp updatedDate;
}
