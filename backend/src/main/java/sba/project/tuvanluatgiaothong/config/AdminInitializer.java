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

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        createDefaultAdmin();
    }

    private void createDefaultAdmin() {
        String adminUsername = "admin";
        String adminEmail = "admin@tvlgt.com";

        try {
            // Check if admin already exists by username or email
            if (userRepository.existsByUsernameAuth(adminUsername) ||
                    userRepository.existsByEmail(adminEmail)) {
                log.info("Admin account already exists, skipping creation");
                return;
            }

            // Convert LocalDate to Instant for birthday
            LocalDate birthDate = LocalDate.of(1990, 1, 1);
            Instant birthInstant = birthDate.atStartOfDay().toInstant(ZoneOffset.UTC);

            // Use Instant.now() for created and updated dates
            Instant now = Instant.now();

            // Create new admin user
            User admin = User.builder()
                    .usernameAuth(adminUsername)
                    .email(adminEmail)
                    .fullname("Administrator")
                    .passwordAuth(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .isEnable(true)
                    .birthDay(birthInstant)
                    .avatarUrl("https://ui-avatars.com/api/?name=Admin&background=007bff&color=fff")
                    .createdDate(now)        // Use Instant instead of LocalDateTime
                    .updatedDate(now)        // Use Instant instead of LocalDateTime
                    .build();

            userRepository.save(admin);
            log.info("Default admin account created successfully");
            log.info("Username: {}", adminUsername);
            log.info("Email: {}", adminEmail);
            log.info("Password: admin123");
            log.warn("Please change the default admin password after first login!");

        } catch (Exception e) {
            log.error("Failed to create default admin account", e);
        }
    }
}