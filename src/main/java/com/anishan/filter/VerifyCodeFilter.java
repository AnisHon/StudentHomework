package com.anishan.filter;

import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
public class VerifyCodeFilter extends GenericFilterBean {

    @Resource
    StringRedisTemplate stringRedisTemplate;


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;



        final String defaultFilterProcessUrl = "/user/login";
        if ("POST".equalsIgnoreCase(request.getMethod()) && defaultFilterProcessUrl.equals(request.getServletPath())) {

            String requestCaptcha = request.getParameter("code");
            String uuid = request.getParameter("uuid");
            String genCaptcha = stringRedisTemplate.opsForValue().get("user:" + uuid + ":code" + requestCaptcha);
            if (requestCaptcha.isEmpty())
                throw new AuthenticationServiceException("验证码不能为空!");

            if (!requestCaptcha.equals(genCaptcha)) {
                throw new AuthenticationServiceException("验证码错误!");
            }
        }
        chain.doFilter(request, response);
    }

}
