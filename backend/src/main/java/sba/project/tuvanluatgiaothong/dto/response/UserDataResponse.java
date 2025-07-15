package sba.project.tuvanluatgiaothong.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDataResponse {

    private String id;
    private String username;
    private String email;
    private String fullname;
    private String avatarUrl;
    private String birthDay;
    private Boolean isEnable = false;
    private String createdDate;
    private String updatedDate;
    private String role;
    private Integer level;
    private Boolean notCreateUsernameAndPassword;

}
