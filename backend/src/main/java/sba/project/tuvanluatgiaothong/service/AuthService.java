package sba.project.tuvanluatgiaothong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sba.project.tuvanluatgiaothong.dto.request.LoginRequest;
import sba.project.tuvanluatgiaothong.dto.response.LoginResponse;
import sba.project.tuvanluatgiaothong.pojo.User;
import sba.project.tuvanluatgiaothong.repository.UserRepository;

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
}
