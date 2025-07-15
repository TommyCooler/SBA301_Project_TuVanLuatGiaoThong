package sba.project.tuvanluatgiaothong.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    public UserMapper(HashingUtil hashingUtil, PasswordEncoder passwordEncoder) {
        this.hashingUtil = hashingUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public User toEntity(RegisterRequest registerRequest) {
        return new User(
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getFullname(),
                "USER".equalsIgnoreCase(registerRequest.getRole()) ? Role.USER : Role.ADMIN
        );
    }

    public User toEntity(RegisterUserRequest registerUserRequest) {
        return new User(
                registerUserRequest.getUsername(),
                registerUserRequest.getEmail(),
                registerUserRequest.getPassword(),
                registerUserRequest.getFullname(),
                Role.USER
        );
    }

    public UserDataResponse toResponse(User user) {
        return new UserDataResponse(
                hashingUtil.hash(user.getId().toString()),
                user.getUsernameAuth(),
                user.getEmail(),
                user.getFullname(),
                user.getAvatarUrl(),
                user.getBirthDay() != null ? user.getBirthDay().toString() : null,
                user.isEnable(),
                user.getCreatedDate() != null ? user.getCreatedDate().toString() : null,
                user.getUpdatedDate() != null ? user.getUpdatedDate().toString() : null,
                user.getRole() != null ? hashingUtil.hash(user.getRole().name()) : null,
                null, // level chưa thấy trong entity nên để null
                user.getPasswordAuth() == null || user.getPasswordAuth().isEmpty()
        );
    }
}
