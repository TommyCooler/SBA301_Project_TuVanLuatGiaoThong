package sba.project.tuvanluatgiaothong.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sba.project.tuvanluatgiaothong.dto.request.PasswordChangeRequest;
import sba.project.tuvanluatgiaothong.dto.request.UpdatingUsernameAndPasswordRequest;
import sba.project.tuvanluatgiaothong.dto.request.UserInfoRequest;
import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
import sba.project.tuvanluatgiaothong.exception.CustomExceptions;
import sba.project.tuvanluatgiaothong.exception.ResourceNotFoundException;
import sba.project.tuvanluatgiaothong.mapper.UserMapper;
import sba.project.tuvanluatgiaothong.pojo.User;
import sba.project.tuvanluatgiaothong.repository.UserRepository;
import sba.project.tuvanluatgiaothong.utils.HashingUtil;


import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final HashingUtil hashingUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse<?> updateInfo(String userId, UserInfoRequest userInfoRequest) {
        String decodedUserId = hashingUtil.decode(userId);
        User user = userRepository.findById(UUID.fromString(decodedUserId))
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Cannot find user by id: " + decodedUserId));

        try {
            user.setFullname(userInfoRequest.getFullname());
            user.setAvatarUrl(userInfoRequest.getAvatarUrl());
            user.setBirthDay(userInfoRequest.getBirthDay());
            user.setUpdatedDate(Instant.now());
            User updatedUser = userRepository.save(user);
            return new ApiResponse<>(
                    userMapper.toResponse(updatedUser),
                    "Update user info successfully",
                    "success"
            );
        } catch (Exception e) {
            throw new CustomExceptions.InternalServerException("An error occurred while updating user info: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<?> updateUsernameAndPassword(String userId, UpdatingUsernameAndPasswordRequest request) {
        String decodedUserId = hashingUtil.decode(userId);
        User user = userRepository.findById(UUID.fromString(decodedUserId))
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find user by id: " + decodedUserId));

        boolean isExistUser = userRepository.existsByUsernameAuth(request.getNewUsername());
        if (isExistUser) {
            return new ApiResponse<>(
//                    null,
                    Optional.empty(),
                    "Tên đăng nhập đã tồn tại, vui lòng chọn tên khác",
                    "fail"
            );
        }

        try {
            if (user.getPasswordAuth() == null || user.getPasswordAuth().isEmpty()) {
                user.setUsernameAuth(request.getNewUsername());
                user.setPasswordAuth(passwordEncoder.encode(request.getNewPassword()));
                user.setUpdatedDate(Instant.now());
                User updatedUser = userRepository.save(user);
                return new ApiResponse<>(
                        userMapper.toResponse(updatedUser),
                        "",
                        "success"
                );
            } else {
                return new ApiResponse<>(
                        Optional.empty(),
                        "Update username and password unsuccessfully, user already has username and password",
                        "fail"
                );
            }
        } catch (Exception e) {
            throw new CustomExceptions.InternalServerException("An error occurred while updating username and password: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<?> changePassword(String userId, PasswordChangeRequest changePasswordRequest) {
        String decodedUserId = hashingUtil.decode(userId);
        User user = userRepository.findById(UUID.fromString(decodedUserId))
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find user by id: " + decodedUserId));

        try {
            if (user.getPasswordAuth() == null || user.getPasswordAuth().isEmpty()) {
                return new ApiResponse<>(
//                        null,
                        Optional.empty(),
                        "Đổi mật khẩu không thành công, người dùng chưa có mật khẩu",
                        "fail"
                );
            }

            if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPasswordAuth())) {
                return new ApiResponse<>(
//                        null,
                        Optional.empty(),
                        "Đổi mật khẩu không thành công!",
                        "fail"
                );
            }

            user.setPasswordAuth(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            User updatedUser = userRepository.save(user);

            return new ApiResponse<>(
                    userMapper.toResponse(updatedUser),
                    "Đổi mật khẩu thành công",
                    "success"
            );

        } catch (Exception e) {
            throw new CustomExceptions.InternalServerException("An error occurred while changing password: " + e.getMessage());
        }
    }
}
