package com.smartshop.UserService.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {
    @Bean
    public OpenAPI swaggerConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Service API")
                        .description("API documentation for the User Service")
                        .version("1.0.0"));
    }
}
