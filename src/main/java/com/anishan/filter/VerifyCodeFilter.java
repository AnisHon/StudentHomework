package com.anishan.filter;

import com.anishan.entity.RestfulEntity;
import com.anishan.service.LoginService;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class VerifyCodeFilter extends GenericFilterBean {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    LoginService loginService;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession();
        String key = "user:" + session.getId() + ":code";



        final String defaultFilterProcessUrl = "/user/login";
        if ("POST".equalsIgnoreCase(request.getMethod()) && defaultFilterProcessUrl.equals(request.getServletPath())) {
            String code = request.getParameter("code");
            if (!loginService.validateCode(session.getId(), code)) {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                PrintWriter writer = response.getWriter();
                writer.write(RestfulEntity.failMessage(406, "验证码错误").toJson());
                return;
            }
        }


        chain.doFilter(request, response);
    }

}
