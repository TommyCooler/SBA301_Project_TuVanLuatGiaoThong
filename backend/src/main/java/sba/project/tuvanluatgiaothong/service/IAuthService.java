package sba.project.tuvanluatgiaothong.service;

import sba.project.tuvanluatgiaothong.dto.request.LoginRequest;
import sba.project.tuvanluatgiaothong.dto.request.RegisterRequest;
import sba.project.tuvanluatgiaothong.dto.response.LoginResponse;
import sba.project.tuvanluatgiaothong.dto.response.RegisterResponse;

public interface IAuthService {
    public LoginResponse loginUser(LoginRequest loginRequest);
    public RegisterResponse registerUser(RegisterRequest registerRequest);
}
