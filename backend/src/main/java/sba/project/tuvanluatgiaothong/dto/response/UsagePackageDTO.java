package sba.project.tuvanluatgiaothong.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UsagePackageDTO {
    private UUID id;
    private String name;
    private String description;
    private Float price;
    private Integer dailyLimit;
    private Integer daysLimit;
    private Boolean isEnable;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
