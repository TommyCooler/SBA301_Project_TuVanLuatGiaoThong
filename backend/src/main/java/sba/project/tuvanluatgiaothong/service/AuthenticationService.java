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
import sba.project.tuvanluatgiaothong.repository.TokenTransaction;
import sba.project.tuvanluatgiaothong.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final TokenTransaction tokenTransaction;
    private final OtpGeneratorUtil otpGeneratorUtil;
    private final EmailService emailService;
    private final RedisService redisService;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final long OTP_DURATION_MIN = 5;

    @Override
    public ApiResponse<?> registerUserWithVerifyingEmail(RegisterUserRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return new ApiResponse<>("fail", "Email đã được đăng ký!", null);
        }

        String otp = otpGeneratorUtil.generateOtp();

        CompletableFuture.runAsync(() -> {
            try {
                emailService.sendOtpEmail(request.getEmail(), otp);
            } catch (Exception e) {
                logger.error("Failed to send OTP email to: " + request.getEmail(), e);
            }
        });

        Map<String, Object> cacheData = Map.of(
                "registerData", request,
                "otp", otp,
                "createdAt", System.currentTimeMillis()
        );

        redisService.setValue("registration:" + request.getEmail(), cacheData, Duration.ofMinutes(OTP_DURATION_MIN));

        return new ApiResponse<>("success", "OTP đã được gửi đến email của bạn. Vui lòng kiểm tra và xác thực trong vòng 5 phút.",
                Map.of("email", request.getEmail()));
    }

    @Override
    public ApiResponse<?> verifyOtp(String email, String otp) {
        String cacheKey = "registration:" + email;
        Map<?, ?> cachedData = (Map<?, ?>) redisService.getValueByKey(cacheKey);
        if (cachedData == null) {
            return new ApiResponse<>("fail", "OTP đã hết hạn hoặc không tồn tại. Vui lòng đăng ký lại.", null);
        }

        String storedOtp = (String) cachedData.get("otp");
        RegisterUserRequest request = (RegisterUserRequest) cachedData.get("registerData");
        Long createdAt = (Long) cachedData.get("createdAt");

        if (storedOtp == null || request == null) {
            redisService.deleteKey(cacheKey);
            return new ApiResponse<>("fail", "Dữ liệu OTP không hợp lệ. Vui lòng đăng ký lại.", null);
        }

        long otpAge = (System.currentTimeMillis() - createdAt) / 1000 / 60;
        if (otpAge > OTP_DURATION_MIN) {
            redisService.deleteKey(cacheKey);
            return new ApiResponse<>("fail", "OTP đã hết hạn. Vui lòng đăng ký lại.", null);
        }

        if (storedOtp.equals(otp)) {
            User user = userMapper.toEntity(request);
            user.setPasswordAuth(passwordEncoder.encode(request.getPassword()));
            user.setIsEnable(true);
            userRepository.save(user);

            return new ApiResponse<>("success", "Đăng ký thành công!",
                    new AuthenticationResponse(jwtService.generateToken(user), jwtService.generateRefreshToken(user)));
        }

        return new ApiResponse<>("fail", "Mã OTP không hợp lệ!", null);
    }

    @Override
    public ApiResponse<?> registerUser(RegisterRequest request) {
        if (userRepository.findByUsernameAuth(request.getUsername()).isPresent()) {
            throw new RuntimeException("User does exist! Please choose another username.");
        }

        User user = userMapper.toEntity(request);
        user.setPasswordAuth(passwordEncoder.encode(request.getPassword()));
        user.setIsEnable(true);
        userRepository.save(user);

        return new ApiResponse<>("success", "Đăng ký thành công!",
                new AuthenticationResponse(jwtService.generateToken(user), jwtService.generateRefreshToken(user)));
    }

    @Override
    public ApiResponse<?> authenticateUser(AuthenticationUserRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (AuthenticationException e) {
            throw new RuntimeException("Username or password is incorrect!");
        }

        User user = getUser(request.getUsername());
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        tokenTransaction.revokeAllOldUserToken(user);
        tokenTransaction.saveToken(user, jwtToken);

        return new ApiResponse<>("success", "Sign in successfully!",
                new AuthenticationResponse(jwtToken, refreshToken));
    }

    @Override
    public ApiResponse<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authHeader = request.getHeader(AUTH_HEADER);
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            return unauthorizedResponse();
        }

        String refreshToken = authHeader.substring(BEARER_PREFIX.length());
        User user = getUser(jwtService.extractUsername(refreshToken));

        if (!jwtService.isValidToken(refreshToken, user)) {
            return unauthorizedResponse();
        }

        String newAccessToken = jwtService.generateToken(user);
        tokenTransaction.revokeAllOldUserToken(user);
        tokenTransaction.saveToken(user, newAccessToken);

        return new ApiResponse<>("success", "Refresh token successfully!",
                new AuthenticationResponse(newAccessToken, refreshToken));
    }

    @Override
    public ApiResponse<?> getUserInfo(HttpServletRequest request, HttpServletResponse response) {
        String authHeader = request.getHeader(AUTH_HEADER);
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            return new ApiResponse<>("unauthorized", "Token is invalid!", null);
        }

        String jwt = authHeader.substring(BEARER_PREFIX.length());
        return new ApiResponse<>("success", "Get user info successfully!",
                userMapper.toResponse(getUser(jwtService.extractUsername(jwt))));
    }

    @Override
    public ApiResponse<List<String>> authenticateToken(String token) {
        User user = getUser(jwtService.extractUsername(token));
        List<String> roles = jwtService.extractRoles(token);

        if (!jwtService.isValidToken(token, user)) {
            return new ApiResponse<>("invalid", "Token is invalid!", null);
        }

        return new ApiResponse<>("valid", "Token is valid!", roles);
    }

    @Override
    public AuthenticationResponse generateToken(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return new AuthenticationResponse(jwtService.generateToken(user), jwtService.generateRefreshToken(user));
    }

    private ApiResponse<?> unauthorizedResponse() {
        return new ApiResponse<>("unauthorized", "Login session is expired!", new AuthenticationResponse("", ""));
    }

    private User getUser(String username) {
        return userRepository.findByUsernameAuth(username).orElseThrow();
    }
}
