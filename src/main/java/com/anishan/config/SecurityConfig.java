package com.anishan.config;

import com.anishan.entity.RestfulEntity;
import com.anishan.filter.VerifyCodeFilter;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Resource
    VerifyCodeFilter filter;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/user/code").permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin(login -> {
                    login.loginProcessingUrl("/user/login");
                    login.successHandler(this::successHandler);
                    login.usernameParameter("username");
                    login.passwordParameter("password");

                    login.failureHandler(this::failureHandler);

                    login.permitAll();
                })
                .cors(cors -> {
                    CorsConfiguration corsConfigurer = new CorsConfiguration();
                    corsConfigurer.addAllowedOriginPattern("*");
                    corsConfigurer.setAllowCredentials(true);
                    corsConfigurer.addAllowedHeader("*");
                    corsConfigurer.addAllowedMethod("*");
                    corsConfigurer.addExposedHeader("*");
                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", corsConfigurer);  //直接针对于所有地址生效
                    cors.configurationSource(source);
                })
                .exceptionHandling(config -> {
                    config.authenticationEntryPoint(this::failureHandler);
                })
                .csrf(CsrfConfigurer::disable)
                .build();
    }

    public void successHandler(HttpServletRequest request,
                               HttpServletResponse response,
                               Authentication authentication)
            throws IOException, ServletException {


        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.write(RestfulEntity.successMessage("登陆成功", "").toJson());


    }

    public void failureHandler(HttpServletRequest request,
                               HttpServletResponse response,
                               Exception exception)
            throws IOException, ServletException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        int code = 404;
        PrintWriter writer = response.getWriter();

        if (exception instanceof AuthenticationException authenticationException) {
            code = 406;
        }

        writer.write(RestfulEntity.failMessage(code, exception.getMessage()).toJson());

    }



}
