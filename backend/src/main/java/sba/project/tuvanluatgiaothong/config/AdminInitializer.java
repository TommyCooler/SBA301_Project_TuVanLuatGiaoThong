package sba.project.tuvanluatgiaothong.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sba.project.tuvanluatgiaothong.enums.Role;
import sba.project.tuvanluatgiaothong.pojo.User;
import sba.project.tuvanluatgiaothong.repository.UserRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminProperties adminProperties;

    @Override
    public void run(String... args) throws Exception {
        if (adminProperties.isEnabled()) {
            createDefaultAdmin();
        }
    }

    private void createDefaultAdmin() {
        try {
            // Check if admin already exists by username or email
            if (userRepository.existsByUsernameAuth(adminProperties.getUsername()) ||
                    userRepository.existsByEmail(adminProperties.getEmail())) {
                log.info("Admin account already exists, skipping creation");
                return;
            }

            // Parse birthday from string to Instant
            LocalDate birthDate = LocalDate.parse(adminProperties.getBirthday(), DateTimeFormatter.ISO_LOCAL_DATE);
            Instant birthInstant = birthDate.atStartOfDay().toInstant(ZoneOffset.UTC);

            // Use Instant.now() for created and updated dates
            Instant now = Instant.now();

            // Create new admin user
            User admin = User.builder()
                    .usernameAuth(adminProperties.getUsername())
                    .email(adminProperties.getEmail())
                    .fullname(adminProperties.getFullname())
                    .passwordAuth(passwordEncoder.encode(adminProperties.getPassword()))
                    .role(Role.ADMIN)
                    .isEnable(true)
                    .birthDay(birthInstant)
                    .avatarUrl(adminProperties.getAvatarUrl())
                    .createdDate(now)
                    .updatedDate(now)
                    .build();

            userRepository.save(admin);
            log.info("Default admin account created successfully");
            log.info("Username: {}", adminProperties.getUsername());
            log.info("Email: {}", adminProperties.getEmail());
            log.warn("Please change the default admin password after first login!");

        } catch (Exception e) {
            log.error("Failed to create default admin account", e);
        }
    }
}