package sba.project.tuvanluatgiaothong.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@PropertySource("classpath:security.properties")
public class SecurityConfig {

    @Value("${app.jwt.signer-key}")
    private String SIGNER_KEY;

    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder(10);
    // }

    @Autowired 
    private RouterValidator routerValidator;
    
    @Autowired
    private RoleRouterValidator roleRouterValidator;

    private final String[] PUBLIC_ENDPOINTS = {"/**"};

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http
    //             .csrf(AbstractHttpConfigurer::disable)
    //             .authorizeHttpRequests(request ->
    //                     request.requestMatchers(PUBLIC_ENDPOINTS).permitAll()
    //                     .anyRequest().authenticated());
    //     http
    //             .oauth2ResourceServer(oauth2 ->
    //                     oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())));


    //     return http.build();
    // }

    @Bean
    JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(),"HS512");
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    private final AuthenticationProvider authenticationProvider;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Value("#{'${app.allowed-origins}'.split(',')}")
    private List<String> allowedOrigins;

    private static final List<String> ALLOWED_METHODS = Arrays.asList("GET", "POST", "PUT", "DELETE");
    private static final List<String> ALLOWED_HEADERS = List.of("*");
    private static final long CORS_MAX_AGE = 3600L;
    private static final String CORS_PATTERN = "/**";
    // private static final String[] OPEN_API = {"/**"};
    private static final String[] OPEN_API = {
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/webjars/**",
        "/api/v1/identity",
        "/api/v1/identity/**",
        "/api/auth/**",
        "/api/v1/chatbot/health",
        "/api/v1/aws/s3/health",
        "/api/v1/law/health",
        "/api/v1/law/get",
        "/api/v1/law/get-all",
        "/api/v1/law/type/get",
        "/api/v1/law/type/get-all",
        "/api/v1/user-packages/usage-package/get-all",
        "/api/v1/user-packages/usage-package/get",
        "/auth/**",
        "/api/v1/chatbot/test-redis",
    };

    public SecurityConfig(AuthenticationProvider authenticationProvider,
                                 OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler) {
        this.authenticationProvider = authenticationProvider;
        this.oAuth2LoginSuccessHandler = oAuth2LoginSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers(OPEN_API).permitAll();
                auth.anyRequest().authenticated();
            })
            .oauth2Login(oauth -> oauth.successHandler(oAuth2LoginSuccessHandler))
            .authenticationProvider(authenticationProvider)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(allowedOrigins);
        config.setAllowedMethods(ALLOWED_METHODS);
        config.setAllowedHeaders(ALLOWED_HEADERS);
        config.setAllowCredentials(true);
        config.setMaxAge(CORS_MAX_AGE);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(CORS_PATTERN, config);
        return source;
    }

}
