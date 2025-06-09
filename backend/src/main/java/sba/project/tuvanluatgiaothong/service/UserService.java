package sba.project.tuvanluatgiaothong.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import sba.project.tuvanluatgiaothong.dto.request.LoginRequest;
import sba.project.tuvanluatgiaothong.dto.response.LoginResponse;
import sba.project.tuvanluatgiaothong.pojo.User;
import sba.project.tuvanluatgiaothong.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserById(UUID userId) {
        // Logic to retrieve a user by ID
        // This would typically involve fetching the user from the database.
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public List<User> getAllUsers() {
        // Logic to retrieve all users
        // This would typically involve fetching all user records from the database.
        return userRepository.findAll();
    }
    
    public User registerUser(String email, String password, String fullname) {
        // Logic to register a new user
        // This would typically involve checking if the email is already registered,
        // hashing the password, and saving the user details to the database.
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already registered");
        }

        User newUser = new User();
        newUser.setEmail(email);
        // newUser.setPassword(passwordEncoder.encode(password));
        newUser.setPassword(password);
        newUser.setFullname(fullname);
        //Trường hợp muốn đặt role User
        // newUser.setRole(User.Role.USER);
        //Trường hợp muốn đặt role Admin
        newUser.setRole(User.Role.ADMIN); 
        newUser.setEnable(true); // Default to enabled
        newUser.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));

        return userRepository.save(newUser);
    }



    public User updateUserProfile(UUID userId, String fullname, String avatarUrl) {
        // Logic to update user profile information
        // This would typically involve fetching the user by ID,
        // updating the fields, and saving the changes to the database.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setFullname(fullname);
        user.setAvatarUrl(avatarUrl);
        user.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        return userRepository.save(user);
    }

    public User updateUserPassword(UUID userId, String newPassword) {
        // Logic to update user password
        // This would typically involve fetching the user by ID,
        // hashing the new password, and saving the changes to the database.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        return userRepository.save(user);
    }

    public void deleteUser(UUID userId) {
        // Logic to delete a user
        // This would typically involve checking if the user exists,
        // and then removing the user from the database.
        userRepository.findById(userId).ifPresent(user -> {
            userRepository.delete(user);
        });
    }
}
