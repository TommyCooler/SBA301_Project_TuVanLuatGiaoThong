package sba.project.tuvanluatgiaothong.dto.request;


import lombok.Data;
import java.time.Instant;

@Data
public class LawRequest {
    String title;
    String referenceNumber;
    String dateline;
    Instant issueDate;
    Instant effectiveDate;
    String sourceUrl;
    String filePath;
    String lawTypeId;
}
