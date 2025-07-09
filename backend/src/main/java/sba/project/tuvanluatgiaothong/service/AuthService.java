package sba.project.tuvanluatgiaothong.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.Builder;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

@Slf4j
@Service
public class AuthService implements IAuthService {
    private final UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${app.jwt.signer-key}")
    private String SIGNER_KEY;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResponse loginUser(LoginRequest loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail());
        if(user == null) {
            throw new IllegalArgumentException("Email not found");
        }

        boolean isPasswordValid = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        if(!isPasswordValid) {
            throw new IllegalArgumentException("Invalid password");
        }
        String token = generateToken(user);
        return new LoginResponse(user.getEmail(), user.getRole(), token, user.getFullName());
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
        newUser.setFullName(registerRequest.getFullName());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setRole(UserRole.USER);
        newUser.setEnable(true);
        newUser.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        userRepository.save(newUser);

        return new RegisterResponse(newUser.getFullName(), newUser.getEmail(), newUser.getPassword(), UserRole.USER, newUser.getCreatedAt(), newUser.isEnable());
    }

    public String generateToken(User user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("sba.project.tuvanluatgiaothong")
                .issueTime(new Timestamp(System.currentTimeMillis()))
                .expirationTime(new Timestamp(System.currentTimeMillis() + 3600000)) // 1 hour expiration
                .claim("scope",user.getRole().toString())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot generate token for email: {}", user.getEmail(), e);
            throw new RuntimeException(e);
        }
    }


}
