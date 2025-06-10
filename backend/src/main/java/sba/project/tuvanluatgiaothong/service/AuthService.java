package sba.project.tuvanluatgiaothong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sba.project.tuvanluatgiaothong.dto.request.LoginRequest;
import sba.project.tuvanluatgiaothong.dto.request.RegisterRequest;
import sba.project.tuvanluatgiaothong.dto.response.LoginResponse;
import sba.project.tuvanluatgiaothong.dto.response.RegisterResponse;
import sba.project.tuvanluatgiaothong.pojo.User;
import sba.project.tuvanluatgiaothong.repository.UserRepository;

import java.sql.Timestamp;

@Service
public class AuthService {
    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResponse loginUser(LoginRequest loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail());
        if(user == null) {
            throw new IllegalArgumentException("Email not found");
        }
        if(!loginRequest.getPassword().equals(user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        return new LoginResponse(user.getEmail(), user.getRole());
    }

    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        User existingUser = userRepository.findByEmail(registerRequest.getEmail());
        if (existingUser != null) {
            throw new IllegalArgumentException("Email already exists");
        }

        User newUser = new User();
        newUser.setFullname(registerRequest.getFullName());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(registerRequest.getPassword());
        newUser.setRole(User.Role.USER);
        newUser.setEnable(true);
        newUser.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        userRepository.save(newUser);

        return new RegisterResponse(newUser.getFullname(), newUser.getEmail(), newUser.getPassword(), User.Role.USER, newUser.getCreatedAt(), newUser.isEnable());
    }
}
