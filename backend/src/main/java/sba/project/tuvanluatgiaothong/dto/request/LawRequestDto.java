package sba.project.tuvanluatgiaothong.dto.request;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class LawRequestDto {
    String title;
    String referenceNumber;
    String dateline;
    Timestamp issueDate;
    Timestamp effectiveDate;
    String sourceUrl;
    String filePath;
    String lawTypeId;
}



