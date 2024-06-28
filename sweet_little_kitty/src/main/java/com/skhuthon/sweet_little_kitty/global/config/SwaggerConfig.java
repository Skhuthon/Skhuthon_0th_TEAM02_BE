package com.skhuthon.sweet_little_kitty.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${server.url}")
    private String serverUrl;

    @Bean
    public OpenAPI openAPI() {

        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.description("개발 환경 서버 URL");

        Server prodServer = new Server();
        prodServer.setUrl(serverUrl);
        prodServer.description("운영 환경 서버 URL");

        Info info = new Info()
                .title("Sweet Little Kitty API")
                .description("Sweet Little Kitty API 명세서")
                .version("v1.0.0");


        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, prodServer));
    }
}
