package sba.project.tuvanluatgiaothong.service;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sba.project.tuvanluatgiaothong.dto.request.AuthenticationUserRequest;
import sba.project.tuvanluatgiaothong.dto.request.RegisterRequest;
import sba.project.tuvanluatgiaothong.dto.request.RegisterUserRequest;
import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
import sba.project.tuvanluatgiaothong.dto.response.AuthenticationResponse;


public interface IAuthenticationService {

    ApiResponse<Object> registerUserWithVerifyingEmail(RegisterUserRequest registerUserRequest);

    ApiResponse<Object> verifyOtp(String email, String sixDigitsOtp);

    ApiResponse<Object> registerUser(RegisterRequest registerRequest);

    ApiResponse<Object> authenticateUser(AuthenticationUserRequest authUserRequest);

    ApiResponse<Object> refreshToken(HttpServletRequest request, HttpServletResponse response);

    ApiResponse<Object> getUserInfo(HttpServletRequest request, HttpServletResponse response);

    ApiResponse<Object> authenticateToken(String token);

    AuthenticationResponse generateToken(String email);
}
