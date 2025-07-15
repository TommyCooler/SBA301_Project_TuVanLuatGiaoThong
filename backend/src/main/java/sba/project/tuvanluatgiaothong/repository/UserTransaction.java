package sba.project.tuvanluatgiaothong.repository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sba.project.tuvanluatgiaothong.pojo.User;


@Service
@Transactional
public class UserTransaction implements IUserTransaction {

    private final UserRepository userRepository;

    public UserTransaction(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean updateUser(User user) {
        try {
            userRepository.save(user);
        } catch (Exception ex) {
            throw new RuntimeException("Cập nhật người dùng thất bại, Id: " + user.getId() + ", Lỗi: " + ex.getMessage(), ex);
        }
        return true;
    }
}
