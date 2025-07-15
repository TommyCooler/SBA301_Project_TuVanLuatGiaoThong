package sba.project.tuvanluatgiaothong.dto.request;

import lombok.Data;

@Data
public class UsagePackageRequest {

    String name;

    String description;

    float price;

    int dailyLimit;

    int daysLimit;

}
