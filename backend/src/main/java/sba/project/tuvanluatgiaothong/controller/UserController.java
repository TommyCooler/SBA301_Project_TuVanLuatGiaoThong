package sba.project.tuvanluatgiaothong.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sba.project.tuvanluatgiaothong.dto.request.LoginRequest;
import sba.project.tuvanluatgiaothong.dto.response.LoginResponse;
import sba.project.tuvanluatgiaothong.pojo.User;
import sba.project.tuvanluatgiaothong.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public String test() {
        return "User API is working!";
    }   

    @GetMapping("/get-by-id")
    public ResponseEntity<User> getUserById(UUID userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }


    @GetMapping("/list-all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


}
