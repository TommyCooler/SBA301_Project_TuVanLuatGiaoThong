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

    ApiResponse<?> registerUserWithVerifyingEmail(RegisterUserRequest registerUserRequest);

    ApiResponse<?> verifyOtp(String email, String sixDigitsOtp);

    ApiResponse<?> registerUser(RegisterRequest registerRequest);

    ApiResponse<?> authenticateUser(AuthenticationUserRequest authUserRequest);

    ApiResponse<?> refreshToken(HttpServletRequest request, HttpServletResponse response);

    ApiResponse<?> getUserInfo(HttpServletRequest request, HttpServletResponse response);

    ApiResponse<List<String>> authenticateToken(String token);

    AuthenticationResponse generateToken(String email);
}
