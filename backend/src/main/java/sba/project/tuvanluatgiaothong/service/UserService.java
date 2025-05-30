package sba.project.tuvanluatgiaothong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import sba.project.tuvanluatgiaothong.pojo.User;
import sba.project.tuvanluatgiaothong.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;
    
    public User registerUser(String email, String password, String fullname) {
        // Logic to register a new user
        // This would typically involve checking if the email is already registered,
        // hashing the password, and saving the user details to the database.
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already registered");
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setFullname(fullname);
        newUser.setRole(User.Role.USER); // Default role
        newUser.setEnable(true); // Default to enabled

        return userRepository.save(newUser);
    }

    public User loginUser(String email, String password) {
        // Logic to authenticate a user
        // This would typically involve checking the email and password against stored values,
        // and returning a user object or an authentication token if successful.
        return null;
    }

    public User updateUserProfile(Long userId, String fullname, String avatarUrl) {
        // Logic to update user profile information
        // This would typically involve fetching the user by ID,
        // updating the fields, and saving the changes to the database.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setFullname(fullname);
        user.setAvatarUrl(avatarUrl);
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        // Logic to delete a user
        // This would typically involve checking if the user exists,
        // and then removing the user from the database.
        userRepository.findById(userId).ifPresent(user -> {
            userRepository.delete(user);
        });
    }
}
