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
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http
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
        PrintWriter writer = response.getWriter();
        writer.write(RestfulEntity.failMessage(406, exception.getMessage()).toJson());

    }



}
