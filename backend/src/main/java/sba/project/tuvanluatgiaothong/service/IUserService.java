package sba.project.tuvanluatgiaothong.service;

import java.util.List;
import java.util.UUID;

import sba.project.tuvanluatgiaothong.pojo.User;

public interface IUserService {
    User getUserById(UUID userId);
    List<User> getAllUsers();
    User updateUserProfile(UUID userId, String fullName, String avatarUrl);
    User updateUserPassword(UUID userId, String oldPassword, String newPassword);
    void deleteUser(UUID userId);
    User updateUserIsEnable(UUID userId, Boolean isEnable);
}
