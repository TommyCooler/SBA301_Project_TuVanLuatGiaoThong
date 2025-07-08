package sba.project.tuvanluatgiaothong.dto.request;

import lombok.Data;

@Data
public class CreateUsagePackageDTO {
    private String name;
    private String description;
    private Float price;
    private Integer dailyLimit;
    private Integer daysLimit;
}