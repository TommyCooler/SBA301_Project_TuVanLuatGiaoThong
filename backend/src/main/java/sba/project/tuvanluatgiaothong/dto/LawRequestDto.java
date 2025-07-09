package sba.project.tuvanluatgiaothong.dto;

import java.sql.Timestamp;
import java.util.UUID;

import lombok.Data;

@Data
public class LawRequestDto {
    String tittle;
    String referenceNumber;
    String dateline;
    Timestamp issueDate;
    Timestamp effectiveDate;
    String sourceUrl;
    String filePath;
    String lawTypeId;
}



