package sba.project.tuvanluatgiaothong.dto;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import lombok.Data;

@Data
public class LawTypeResponseDTO {

    UUID id;

    String name;

    boolean isDeleted;

    Timestamp createdDate;

    Timestamp updatedDate;

}