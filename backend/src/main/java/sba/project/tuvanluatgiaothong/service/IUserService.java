package sba.project.tuvanluatgiaothong.service;


import sba.project.tuvanluatgiaothong.dto.request.PasswordChangeRequest;
import sba.project.tuvanluatgiaothong.dto.request.UpdatingUsernameAndPasswordRequest;
import sba.project.tuvanluatgiaothong.dto.request.UserInfoRequest;
import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;

public interface IUserService {
    ApiResponse<?> updateInfo(String userId, UserInfoRequest userInfoRequest);

    ApiResponse<?> updateUsernameAndPassword(String userId, UpdatingUsernameAndPasswordRequest request);

    ApiResponse<?> changePassword(String userId, PasswordChangeRequest changePasswordRequest);
}
