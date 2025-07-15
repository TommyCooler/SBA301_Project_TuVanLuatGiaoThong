package sba.project.tuvanluatgiaothong.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sba.project.tuvanluatgiaothong.dto.request.PasswordChangeRequest;
import sba.project.tuvanluatgiaothong.dto.request.UpdatingUsernameAndPasswordRequest;
import sba.project.tuvanluatgiaothong.dto.request.UserInfoRequest;
import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
import sba.project.tuvanluatgiaothong.service.IUserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PutMapping("/update/{userId}")
    public ResponseEntity<ApiResponse<?>> updateUserInfo(
            @PathVariable("userId") String userId,
            @RequestBody UserInfoRequest userInfoRequest) {
        return new ResponseEntity<>(userService.updateInfo(userId, userInfoRequest), HttpStatus.OK);
    }

    @PutMapping("/update/username-password/{userId}")
    public ResponseEntity<ApiResponse<?>> updateUsernameAndPassword(
            @PathVariable("userId") String userId,
            @RequestBody UpdatingUsernameAndPasswordRequest request) {
        return new ResponseEntity<>(userService.updateUsernameAndPassword(userId, request), HttpStatus.OK);
    }

    @PutMapping("/change-password/{userId}")
    public ResponseEntity<ApiResponse<?>> changePassword(
            @PathVariable("userId") String userId,
            @RequestBody PasswordChangeRequest changePasswordRequest) {
        return new ResponseEntity<>(userService.changePassword(userId, changePasswordRequest), HttpStatus.OK);
    }
}
