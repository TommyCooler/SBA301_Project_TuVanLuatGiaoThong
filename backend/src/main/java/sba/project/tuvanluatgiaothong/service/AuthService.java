package sba.project.tuvanluatgiaothong.service;

import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sba.project.tuvanluatgiaothong.dto.request.LoginRequest;
import sba.project.tuvanluatgiaothong.dto.request.RegisterRequest;
import sba.project.tuvanluatgiaothong.dto.response.LoginResponse;
import sba.project.tuvanluatgiaothong.dto.response.RegisterResponse;
import sba.project.tuvanluatgiaothong.enums.UserRole;
import sba.project.tuvanluatgiaothong.pojo.User;
import sba.project.tuvanluatgiaothong.repository.UserRepository;

import java.sql.Timestamp;

@Service
public class AuthService implements IAuthService {
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
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean isPasswordValid = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        if(!isPasswordValid) {
            throw new IllegalArgumentException("Invalid password");
        }
        return new LoginResponse(user.getEmail(), user.getRole());
    }

    @Transactional
    public RegisterResponse registerUser(RegisterRequest registerRequest) {


        User existingUser = userRepository.findByEmail(registerRequest.getEmail());
        if (existingUser != null) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        User newUser = new User();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        newUser.setFullName(registerRequest.getFullName());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setRole(UserRole.USER);
        newUser.setEnable(true);
        newUser.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        userRepository.save(newUser);

        return new RegisterResponse(newUser.getFullName(), newUser.getEmail(), newUser.getPassword(), UserRole.USER, newUser.getCreatedAt(), newUser.isEnable());
    }
}
