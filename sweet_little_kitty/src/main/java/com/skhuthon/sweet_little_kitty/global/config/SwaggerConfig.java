package com.skhuthon.sweet_little_kitty.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Sweet Little Kitty API")
                .description("Sweet Little Kitty API 명세서")
                .version("v1.0.0");

        return new OpenAPI().info(info);
    }
}
