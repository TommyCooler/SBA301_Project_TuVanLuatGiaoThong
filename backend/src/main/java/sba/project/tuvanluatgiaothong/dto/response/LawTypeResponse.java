package sba.project.tuvanluatgiaothong.dto.response;

import java.time.Instant;
import java.util.UUID;

import lombok.Data;

@Data
public class LawTypeResponse {

    UUID id;

    String name;

    boolean isDeleted;

    Instant createdDate;

    Instant updatedDate;

}