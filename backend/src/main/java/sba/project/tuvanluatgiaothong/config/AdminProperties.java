package sba.project.tuvanluatgiaothong.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.admin.default")
public class AdminProperties {
    private String username = "admin";
    private String email = "admin@tvlgt.com";
    private String password = "admin123";
    private String fullname = "Administrator";
    private String avatarUrl = "https://ui-avatars.com/api/?name=Admin&background=007bff&color=fff";
    private boolean enabled = true;
    private String birthday = "1990-01-01";
}