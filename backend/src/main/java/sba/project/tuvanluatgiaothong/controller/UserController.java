package sba.project.tuvanluatgiaothong.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/register")
    public User registerUser(String email, String password, String fullname) {
        //Mẫu Test account
        return userService.registerUser("admin@gmail.com", "12345", "Admin User");
        // return userService.registerUser(email, password, fullname);
    }
}
