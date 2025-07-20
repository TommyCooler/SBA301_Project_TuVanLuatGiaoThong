package sba.project.tuvanluatgiaothong.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import sba.project.tuvanluatgiaothong.dto.request.AuthenticationUserRequest;
import sba.project.tuvanluatgiaothong.dto.request.RegisterRequest;
import sba.project.tuvanluatgiaothong.dto.request.RegisterUserRequest;
import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
import sba.project.tuvanluatgiaothong.dto.response.AuthenticationResponse;
import sba.project.tuvanluatgiaothong.mapper.UserMapper;
import sba.project.tuvanluatgiaothong.pojo.User;
import sba.project.tuvanluatgiaothong.repository.ITokenTransaction;
import sba.project.tuvanluatgiaothong.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final ITokenTransaction tokenTransaction;
    private final OtpGeneratorUtil optGeneratorUtil;
    private final EmailService emailService;
    private final RedisService redisService;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public static final String AUTHENTICATION_HEADER = "Authorization";
    public static final String AUTHENTICATION_HEADER_BEARER = "Bearer ";
    public static final long OTP_DURATION_MIN = 5L;

    @Override
    public ApiResponse<Object> registerUserWithVerifyingEmail(RegisterUserRequest req) {
        boolean emailExists = userRepository.findByEmail(req.getEmail()).isPresent();
        if (emailExists) {
            return new ApiResponse<>("fail", "Email đã được đăng ký!", Optional.ofNullable(null));
        }

        String otp = optGeneratorUtil.generateOtp();

        // Send email asynchronously
        CompletableFuture.runAsync(() -> {
            try {
                emailService.sendOtpEmail(req.getEmail(), otp);
            } catch (Exception e) {
                logger.error("Failed to send OTP email to: " + req.getEmail(), e);
            }
        });

        // Cache registration data + OTP in Redis
        Map<String, Object> cacheData = new HashMap<>();
        cacheData.put("registerData", req);
        cacheData.put("otp", otp);
        cacheData.put("createdAt", System.currentTimeMillis());

        redisService.setValue(
                "registration:" + req.getEmail(),
                cacheData,
                Duration.ofMinutes(OTP_DURATION_MIN)
        );

        return new ApiResponse<>(
                "success",
                "OTP đã được gửi đến email của bạn. Vui lòng kiểm tra và xác thực trong vòng 5 phút.",
                Collections.singletonMap("email", req.getEmail())
        );
    }

    @Override
    public ApiResponse<Object> verifyOtp(String email, String providedOtp) {
        String key = "registration:" + email;
        @SuppressWarnings("unchecked")
        Map<String, Object> cached = (Map<String, Object>) redisService.getValueByKey(key);

        if (cached == null) {
            return new ApiResponse<>("fail", "OTP đã hết hạn hoặc không tồn tại. Vui lòng đăng ký lại.", Optional.ofNullable(null));
        }

        String storedOtp = (String) cached.get("otp");
        RegisterUserRequest req = (RegisterUserRequest) cached.get("registerData");
        Long createdAt = (Long) cached.get("createdAt");

        if (storedOtp == null || req == null) {
            redisService.deleteKey(key);
            return new ApiResponse<>("fail", "Dữ liệu OTP không hợp lệ. Vui lòng đăng ký lại.", Optional.ofNullable(null));
        }

        long ageMinutes = (System.currentTimeMillis() - createdAt) / 1000 / 60;
        if (ageMinutes > OTP_DURATION_MIN) {
            redisService.deleteKey(key);
            return new ApiResponse<>("fail", "OTP đã hết hạn. Vui lòng đăng ký lại.", Optional.ofNullable(null));
        }

        if (!storedOtp.equals(providedOtp)) {
            return new ApiResponse<>("fail", "Mã OTP không hợp lệ!", Optional.ofNullable(null));
        }

        // OTP valid → create user
        User user = userMapper.toEntity(req);
        user.setPasswordAuth(passwordEncoder.encode(req.getPassword()));
        user.setIsEnable(true);
        userRepository.save(user);

        AuthenticationResponse authResp = new AuthenticationResponse(
                jwtService.generateToken(user),
                jwtService.generateRefreshToken(user)
        );
        return new ApiResponse<>("success", "Đăng ký thành công!", authResp);
    }

    @Override
    public ApiResponse<Object> registerUser(RegisterRequest req) {
        if (isExistUsername(req.getUsername())) {
            throw new RuntimeException("User does exist! Please choose another username.");
        }

        User user = userMapper.toEntity(req);
        user.setPasswordAuth(passwordEncoder.encode(req.getPassword()));
        user.setIsEnable(true);
        userRepository.save(user);

        AuthenticationResponse authResp = new AuthenticationResponse(
                jwtService.generateToken(user),
                jwtService.generateRefreshToken(user)
        );
        return new ApiResponse<>("success", "Đăng ký thành công!", authResp);
    }

    @Override
    public ApiResponse<Object> authenticateUser(AuthenticationUserRequest req) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new RuntimeException("Tên đăng nhập hoặc mật khẩu không đúng!");
        }

        User user = getUser(req.getUsername());
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        tokenTransaction.revokeAllOldUserToken(user);
        tokenTransaction.saveToken(user, accessToken);

        AuthenticationResponse authResp = new AuthenticationResponse(accessToken, refreshToken);
        return new ApiResponse<>("success", "Sign in successfully!", authResp);
    }

    @Override
    public ApiResponse<Object> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String header = request.getHeader(AUTHENTICATION_HEADER);
        if (header == null || !header.startsWith(AUTHENTICATION_HEADER_BEARER)) {
            return unauthorizedResponse();
        }

        String refreshToken = header.substring(AUTHENTICATION_HEADER_BEARER.length());
        String username = jwtService.extractUsername(refreshToken);
        User user = getUser(username);

        if (!jwtService.isValidToken(refreshToken, user)) {
            return unauthorizedResponse();
        }

        String newAccessToken = jwtService.generateToken(user);
        tokenTransaction.revokeAllOldUserToken(user);
        tokenTransaction.saveToken(user, newAccessToken);

        AuthenticationResponse authResp = new AuthenticationResponse(newAccessToken, refreshToken);
        return new ApiResponse<>("success", "Refresh token successfully!", authResp);
    }

    private ApiResponse<Object> unauthorizedResponse() {
        return new ApiResponse<>(
                "unauthorized",
                "Login session is expired!",
                new AuthenticationResponse("", "")
        );
    }

    @Override
    public ApiResponse<Object> getUserInfo(HttpServletRequest request, HttpServletResponse response) {
        String header = request.getHeader(AUTHENTICATION_HEADER);
        if (header == null || !header.startsWith(AUTHENTICATION_HEADER_BEARER)) {
            return new ApiResponse<>("unauthorized", "Token is invalid!", Optional.ofNullable(null));
        }

        String token = header.substring(AUTHENTICATION_HEADER_BEARER.length());
        String username = jwtService.extractUsername(token);
        User user = getUser(username);

        return new ApiResponse<>("success", "Get user info successfully!",
                userMapper.toResponse(user)
        );
    }

    @Override
    public ApiResponse<Object> authenticateToken(String token) {
        String username = jwtService.extractUsername(token);
        User user = getUser(username);
        if (!jwtService.isValidToken(token, user)) {
            return new ApiResponse<>("invalid", "Token is invalid!", Optional.empty());
        }
        return new ApiResponse<>("valid", "Token is valid!", jwtService.extractRoles(token));
    }

    @Override
    public AuthenticationResponse generateToken(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return new AuthenticationResponse(
                jwtService.generateToken(user),
                jwtService.generateRefreshToken(user)
        );
    }

    private boolean isExistUsername(String username) {
        return userRepository.findByUsernameAuth(username).isPresent();
    }

    private User getUser(String username) {
        return userRepository.findByUsernameAuth(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }
}

