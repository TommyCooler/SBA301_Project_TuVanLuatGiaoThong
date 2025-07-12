// package sba.project.tuvanluatgiaothong.service;

// import java.util.List;
// import java.util.UUID;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// import sba.project.tuvanluatgiaothong.pojo.User;
// import sba.project.tuvanluatgiaothong.repository.UserRepository;

// @Service
// public class UserService implements IUserService {

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private PasswordEncoder passwordEncoder;

//     public User getUserById(UUID userId) {
//         // Logic to retrieve a user by ID
//         // This would typically involve fetching the user from the database.
//         return userRepository.findById(userId)
//                 .orElseThrow(() -> new IllegalArgumentException("User not found"));
//     }

//     public List<User> getAllUsers() {
//         // Logic to retrieve all users
//         // This would typically involve fetching all user records from the database.
//         return userRepository.findAll();
//     }
    


//     public User updateUserProfile(UUID userId, String fullName, String avatarUrl) {
//         User user = userRepository.findById(userId)
//                 .orElseThrow(() -> new IllegalArgumentException("User not found"));
//         user.setFullName(fullName);
//         user.setAvatarUrl(avatarUrl);
//         user.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()).toInstant());
//         return userRepository.save(user);
//     }

//     @Override
//     public User updateUserPassword(UUID userId, String oldPassword, String newPassword) {
//         return null;
//     }

//     public User updateUserPassword(UUID userId, String newPassword) {
//         // Logic to update user password
//         // This would typically involve fetching the user by ID,
//         // hashing the new password, and saving the changes to the database.
//         User user = userRepository.findById(userId)
//                 .orElseThrow(() -> new IllegalArgumentException("User not found"));
//         user.setPassword(passwordEncoder.encode(newPassword));
//         user.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()).toInstant());
//         return userRepository.save(user);
//     }

//     public void deleteUser(UUID userId) {
//         userRepository.findById(userId).ifPresent(userRepository::delete);
//     }

//     public User updateUserIsEnable(UUID userId, Boolean isEnable) {
//         User user = userRepository.findById(userId)
//                 .orElseThrow(() -> new IllegalArgumentException("User not found"));
//         user.setEnable(isEnable);
//         user.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()).toInstant());
//         return userRepository.save(user);
//     }
// }
