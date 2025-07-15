package sba.project.tuvanluatgiaothong.service;

import org.springframework.stereotype.Service;

import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
import sba.project.tuvanluatgiaothong.exception.UserNotFoundException;
import sba.project.tuvanluatgiaothong.mapper.UserMapper;
import sba.project.tuvanluatgiaothong.pojo.User;
import sba.project.tuvanluatgiaothong.repository.IUserTransaction;
import sba.project.tuvanluatgiaothong.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserManagementService implements IUserManagementService {

    private final IUserTransaction userTransaction;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserManagementService(IUserTransaction userTransaction, UserRepository userRepository, UserMapper userMapper) {
        this.userTransaction = userTransaction;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public ApiResponse<Object> getAllUsers() {
        List<Object> mappedUsers = userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());

        return new ApiResponse<>(
                "success",
                "Lấy danh sách người dùng thành công",
                mappedUsers
        );
    }

    @Override
    public ApiResponse<Object> disableUser(String id) {
        User user = userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy người dùng mang Id: " + id));

        user.setIsEnable(false);

        userTransaction.updateUser(user);

        return new ApiResponse<>(
                "success",
                "Người dùng đã bị khóa hóa thành công",
                Optional.empty()
        );
    }
}
