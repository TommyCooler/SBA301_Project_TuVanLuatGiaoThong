package sba.project.tuvanluatgiaothong.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import sba.project.tuvanluatgiaothong.enums.UserRole;
import sba.project.tuvanluatgiaothong.pojo.User;
import sba.project.tuvanluatgiaothong.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User registerUser(String email, String password, String fullName) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already registered");
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setFullName(fullName);
        newUser.setRole(UserRole.USER);
        newUser.setEnable(true);
        newUser.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));

        return userRepository.save(newUser);
    }

    public User loginUser(String email, String password) {
        return null;
    }

    public User updateUserProfile(UUID userId, String fullName, String avatarUrl) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setFullName(fullName);
        user.setAvatarUrl(avatarUrl);
        user.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        return userRepository.save(user);
    }

    public User updateUserPassword(UUID userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        return userRepository.save(user);
    }

    public void deleteUser(UUID userId) {
        userRepository.findById(userId).ifPresent(userRepository::delete);
    }
}
