package sba.project.tuvanluatgiaothong.mapper;

import org.springframework.stereotype.Component;

import sba.project.tuvanluatgiaothong.dto.request.RegisterRequest;
import sba.project.tuvanluatgiaothong.dto.request.RegisterUserRequest;
import sba.project.tuvanluatgiaothong.dto.response.UserDataResponse;
import sba.project.tuvanluatgiaothong.enums.Role;
import sba.project.tuvanluatgiaothong.pojo.User;
import sba.project.tuvanluatgiaothong.utils.HashingUtil;

@Component("userMapper_IdentityService")
public class UserMapper {

    private final HashingUtil hashingUtil;

    public UserMapper(HashingUtil hashingUtil) {
        this.hashingUtil = hashingUtil;
    }

    public User toEntity(RegisterRequest registerRequest) {
        return User.builder()
            .usernameAuth(registerRequest.getUsername())
            .email(registerRequest.getEmail())
            .passwordAuth(registerRequest.getPassword())
            .fullname(registerRequest.getFullname())
            .role("USER".equalsIgnoreCase(registerRequest.getRole()) ? Role.USER : Role.ADMIN)
            .build();
    }

    public User toEntity(RegisterUserRequest registerUserRequest) {
        return User.builder()
            .usernameAuth(registerUserRequest.getUsername())
            .email(registerUserRequest.getEmail())
            .passwordAuth(registerUserRequest.getPassword())
            .fullname(registerUserRequest.getFullname())
            .role(Role.USER)
            .build();
    }

    public UserDataResponse toResponse(User user) {
        return UserDataResponse.builder()
            .id(hashingUtil.hash(user.getId().toString()))
            .username(user.getUsernameAuth())
            .email(user.getEmail())
            .fullname(user.getFullname())
            .avatarUrl(user.getAvatarUrl())
            .birthDay(user.getBirthDay() != null ? user.getBirthDay().toString() : null)
            .isEnable(user.isEnable())
            .role(user.getRole() != null ? hashingUtil.hash(user.getRole().name()) : null)
            .createdDate(user.getCreatedDate().toString())
            .updatedDate(user.getUpdatedDate().toString())
            .build();
    }
}
