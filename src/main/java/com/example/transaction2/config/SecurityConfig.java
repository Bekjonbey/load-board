package com.example.transaction2.config;

import com.example.transaction2.security.JwtAuthenticationEntryPoint;
import com.example.transaction2.security.JwtAuthenticationFilter;
import com.example.transaction2.util.RestConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final MyAccessDeniedHandler myAccessDeniedHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests(
                        auth ->
                                auth
                                        .requestMatchers(RestConstants.OPEN_PAGES)
                                        .permitAll()
                                        .requestMatchers(HttpMethod.OPTIONS)
                                        .permitAll()
                                        .requestMatchers("/",
                                                "/favicon.ico",
                                                "//*.png",
                                                "//*.gif",
                                                "//*.svg",
                                                "//*.jpg",
                                                "//*.html",
                                                "//*.css",
                                                "//*.js",
                                                "/swagger-ui.html",
                                                "/swagger-ui/**",
                                                "/swagger-resources/**",
                                                "/v2/",
                                                "/csrf",
                                                "/webjars/**",
                                                "/v2/api-docs",
                                                "/configuration/ui")
                                        .permitAll()
                                        .requestMatchers("/api/**")
                                        .authenticated())
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(myAccessDeniedHandler)
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }
}
