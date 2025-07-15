package sba.project.tuvanluatgiaothong.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sba.project.tuvanluatgiaothong.enums.Role;
import sba.project.tuvanluatgiaothong.pojo.User;
import sba.project.tuvanluatgiaothong.repository.UserRepository;
import sba.project.tuvanluatgiaothong.service.JwtService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@PropertySource("classpath:security.properties")
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    
    private final UserRepository userRepository;

    @Value("${frontend.oauth2.redirect-url}")
    private String oauth2RedirectUrl;

    public OAuth2LoginSuccessHandler(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        if (email == null) {
            throw new RuntimeException("Email attribute not found in OAuth2User");
        }

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> registerNewUser(oAuth2User));

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        String redirectUrl = String.format("%s?accessToken=%s&refreshToken=%s",
                oauth2RedirectUrl, accessToken, refreshToken);

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

    private User registerNewUser(OAuth2User oAuth2User) {
        User newUser = new User();
        newUser.setEmail(oAuth2User.getAttribute("email"));
        newUser.setUsernameAuth(UUID.randomUUID().toString());
        newUser.setFullname(oAuth2User.getAttribute("name"));
        newUser.setIsEnable(true);
        newUser.setRole(Role.USER);
        return userRepository.save(newUser);
    }
}
