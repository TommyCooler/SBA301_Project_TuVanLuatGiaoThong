package sba.project.tuvanluatgiaothong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sba.project.tuvanluatgiaothong.dto.ApiResponse;
import sba.project.tuvanluatgiaothong.dto.request.LoginRequest;
import sba.project.tuvanluatgiaothong.dto.response.LoginResponse;
import sba.project.tuvanluatgiaothong.service.AuthService;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        LoginResponse result =  authService.loginUser(loginRequest) ;
        return new ApiResponse<>("Success", "Login successfully", result);
    }
}
