package project.travelmate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import project.travelmate.interceptor.LoginInterceptor;
import project.travelmate.util.JwtUtil;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    @Bean
    public LoginInterceptor loginInterceptor(JwtUtil jwtUtil) {
        return new LoginInterceptor(jwtUtil);
    }

}