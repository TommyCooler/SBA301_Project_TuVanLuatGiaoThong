package sba.project.tuvanluatgiaothong.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationUserRequest implements Serializable {
    private String username;
    private String password;
}
