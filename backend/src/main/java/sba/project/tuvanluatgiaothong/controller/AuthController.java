// package sba.project.tuvanluatgiaothong.controller;

// import jakarta.validation.Valid;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
// import sba.project.tuvanluatgiaothong.dto.request.LoginRequest;
// import sba.project.tuvanluatgiaothong.dto.request.RegisterRequest;
// import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
// import sba.project.tuvanluatgiaothong.dto.response.LoginResponse;
// import sba.project.tuvanluatgiaothong.dto.response.RegisterResponse;
// import sba.project.tuvanluatgiaothong.service.AuthService;

// @RestController
// @RequestMapping("/api/v1")
// public class AuthController {
//     @Autowired
//     private AuthService authService;

//     @PostMapping("/login")
//     public ApiResponse<LoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
//         LoginResponse result =  authService.loginUser(loginRequest) ;
//         return new ApiResponse<>("Success", "Login successfully", result);
//     }

//     @PostMapping("/register")
//     public ApiResponse<RegisterResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
//         RegisterResponse result = authService.registerUser(registerRequest);
//         return new ApiResponse<>("Success", "Registration successful", result);
//     }
// }
