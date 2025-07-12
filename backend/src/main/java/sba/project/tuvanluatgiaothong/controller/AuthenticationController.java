package sba.project.tuvanluatgiaothong.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import sba.project.tuvanluatgiaothong.dto.request.AuthenticationUserRequest;
import sba.project.tuvanluatgiaothong.dto.request.RegisterRequest;
import sba.project.tuvanluatgiaothong.dto.request.RegisterUserRequest;
import sba.project.tuvanluatgiaothong.dto.request.VerifyOptRequest;
import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
import sba.project.tuvanluatgiaothong.service.IAuthenticationService;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/identity")
@RequiredArgsConstructor
public class AuthenticationController {

    private final IAuthenticationService authenticationService;
    private final RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/health")
    public String healthCheck() {
        return "Identity service is up and running!";
    }

    @PostMapping("/register-with-verifying")
    public ResponseEntity<ApiResponse<?>> registerUserWithVerifyingEmail(@RequestBody RegisterUserRequest request) {
        return new ResponseEntity<>(authenticationService.registerUserWithVerifyingEmail(request), HttpStatus.OK);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<?>> verifyOtp(@RequestBody VerifyOptRequest request) {
        return new ResponseEntity<>(
                authenticationService.verifyOtp(request.getEmail(), request.getSixDigitsOtp()),
                HttpStatus.OK
        );
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> registerUser(@RequestBody RegisterRequest request) {
        return new ResponseEntity<>(authenticationService.registerUser(request), HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse<?>> authenticateUser(@RequestBody AuthenticationUserRequest request) {
        return new ResponseEntity<>(authenticationService.authenticateUser(request), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<?>> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(authenticationService.refreshToken(request, response), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<?>> getUserInfo(HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(authenticationService.getUserInfo(request, response), HttpStatus.OK);
    }

    @PostMapping("/authenticate/token/{token}")
    public ResponseEntity<ApiResponse<?>> authenticateToken(@PathVariable("token") String token) {
        return new ResponseEntity<>(authenticationService.authenticateToken(token), HttpStatus.OK);
    }

    @GetMapping("/test-redis")
    public ResponseEntity<Map<String, String>> testRedisConnection() {
        String status;
        try {
            redisTemplate.opsForValue().set("test:ping", "pong", 10, TimeUnit.SECONDS);
            Object result = redisTemplate.opsForValue().get("test:ping");
            status = "Redis connection successful: " + result;
        } catch (Exception e) {
            status = "Redis connection failed: " + e.getMessage();
        }
        return ResponseEntity.ok(Map.of("status", status));
    }
}
