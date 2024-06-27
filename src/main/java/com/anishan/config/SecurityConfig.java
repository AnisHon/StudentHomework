package com.anishan.config;

import com.anishan.controller.LoginController;
import com.anishan.entity.RestfulEntity;
import com.anishan.filter.VerifyCodeFilter;
import com.anishan.service.LoginService;
import com.anishan.service.impl.LoginServiceImpl;
import com.anishan.tool.EnumRole;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
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

@EnableAsync
@Configuration
public class SecurityConfig {

    @Resource
    VerifyCodeFilter filter;
    @Resource
    private LoginService loginService;


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/student/me").hasAnyRole(EnumRole.STUDENT, EnumRole.STUDENT_REPRESENTATIVE);
                    auth.requestMatchers("/teacher/student/", "/teacher/search-student/",
                            "/teacher/students/", "/teacher/me"
                    ).hasRole(EnumRole.TEACHER);
                    auth.requestMatchers("/admin/**").hasRole(EnumRole.ADMIN);
                    auth.requestMatchers("/teacher/**").hasAnyRole(EnumRole.TEACHER, EnumRole.ADMIN, EnumRole.STUDENT_REPRESENTATIVE);
                    auth.requestMatchers("/student/**").hasAnyRole(
                            EnumRole.STUDENT, EnumRole.STUDENT_REPRESENTATIVE, EnumRole.TEACHER, EnumRole.ADMIN);
                    auth.anyRequest().permitAll();
                })
                .formLogin(login -> {
                    login.loginProcessingUrl("/user/login");
                    login.successHandler(this::successHandler);
                    login.usernameParameter("username");
                    login.passwordParameter("password");
                    login.failureHandler(this::failureHandler);
                    login.permitAll();
                })
                .logout(logout -> {
                    logout.logoutUrl("/user/logout");
                    logout.logoutSuccessHandler(this::successLogoutHandler);
                    logout.permitAll();
                })
                .rememberMe(config -> {
                    config.rememberMeParameter("remember");
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
                    config.accessDeniedHandler(this::failureHandler);
                })
                .csrf(CsrfConfigurer::disable)
                .build();
    }

    private void successLogoutHandler(HttpServletRequest httpServletRequest,
                                      HttpServletResponse response,
                                      Authentication authentication) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.write(RestfulEntity.plainSuccessMessage("登出成功").toJson());

    }

    public void successHandler(HttpServletRequest request,
                               HttpServletResponse response,
                               Authentication authentication)
            throws IOException {


        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        RestfulEntity<?> me = loginService.getMe(authentication);
        writer.write(me.toJsonWithoutNull());


    }

    public void failureHandler(HttpServletRequest request,
                               HttpServletResponse response,
                               Exception exception)
            throws IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        int code = 404;
        String message = exception.getMessage();
        PrintWriter writer = response.getWriter();

        if (exception instanceof InsufficientAuthenticationException) {
            code = 405;
            message = "无权限";
        } else if (exception instanceof AuthenticationException) {
            code = 406;
        } else if (exception instanceof AccessDeniedException) {
            code = 405;
            message = "拒绝访问";
        }
        System.out.println(exception.getClass());

        writer.write(RestfulEntity.failMessage(code, message).toJson());

    }



}
