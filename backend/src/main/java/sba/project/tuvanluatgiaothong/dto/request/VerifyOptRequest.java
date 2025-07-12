package sba.project.tuvanluatgiaothong.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyOptRequest implements Serializable {
    private String email;
    private String sixDigitsOtp;
}
