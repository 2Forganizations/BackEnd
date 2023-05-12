package project.travelmate.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import project.travelmate.util.JwtUtil;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final PrincipalDetailService principalDetailService;
    private final JwtUtil jwtUtil;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().mvcMatchers(
                "/login/oauth2/kakao",
                "/1"
        );
    }

    /**
     * Spring security에 관한 빈 등록 -> WebSecurityConfigurerAdapter 가 deprecated 되면서
     * 방식이 override 에서 bean 등록으로 바뀌었다.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(principalDetailService).passwordEncoder(passwordEncoder());
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/api/*").permitAll()
                .antMatchers("/api/**").authenticated() //이 패턴 인증필요하다
                .anyRequest().permitAll()
                .and()
                .formLogin().disable()
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtAuthorizationFilter(authenticationManager, jwtUtil))
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .authenticationManager(authenticationManager)
                .cors().configurationSource(corsConfigurationSource());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }


    /**
     * spring security 에 대한 cors config
     * 전부 열어놓은 것에 대하여 조사 후 제한 필요
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}