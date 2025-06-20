package sba.project.tuvanluatgiaothong.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sba.project.tuvanluatgiaothong.enums.UserRole;
import sba.project.tuvanluatgiaothong.pojo.User;
import sba.project.tuvanluatgiaothong.repository.UserRepository;

import java.sql.Timestamp;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ApplicationInitConfig {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    protected ApplicationRunner init(UserRepository userRepository) {
        return args -> {
            if(userRepository.findByEmail("admin@gmail.com") == null) {
                User user = new User();
                user.setFullName("Admin");
                user.setEmail("admin@gmail.com");
                user.setPassword(passwordEncoder.encode("123456"));
                user.setRole(UserRole.ADMIN);
                user.setEnable(true);
                user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                userRepository.save(user);
                log.warn("Admin user created with email:" + user.getEmail());
            }

        };

    }
}
