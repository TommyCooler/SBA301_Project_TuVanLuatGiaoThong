package sba.project.tuvanluatgiaothong.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Tư vấn Luật Giao Thông API")
                        .version("v1")
                        .description("API documentation for Tư vấn Luật Giao Thông system"));
    }
}