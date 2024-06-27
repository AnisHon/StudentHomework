package com.anishan.service.impl;

import com.anishan.entity.*;
import com.anishan.service.AccountService;
import com.anishan.service.LoginService;
import com.anishan.service.StudentService;
import com.anishan.service.TeacherService;
import com.anishan.tool.EnumRole;
import com.wf.captcha.SpecCaptcha;
import jakarta.annotation.Resource;
import lombok.Data;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.wf.captcha.base.Captcha.TYPE_DEFAULT;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private StudentService studentService;
    @Resource
    private AccountService accountService;
    @Resource
    private TeacherService teacherService;

    @Override
    public String getValidationCode(String sessionId) {
        String key = "user:" + sessionId + ":code";
        stringRedisTemplate.delete(key);
        
        SpecCaptcha captcha = new SpecCaptcha(130, 48);
        stringRedisTemplate.opsForValue().set(key, captcha.text(), TIME_OUT, TimeUnit.SECONDS);
        return RestfulEntity.successMessage("success", captcha.toBase64()).toJson();
    }

    @Override
    public boolean validateCode(String sessionId, String code) {

        String key = "user:" + sessionId + ":code";
        String s = stringRedisTemplate.opsForValue().get(key);
        if (s == null || s.isEmpty()) {
            return false;
        }
        stringRedisTemplate.delete(key);
        return s.equalsIgnoreCase(code);
    }

@Data
static class AccountJson {
        Account account;
        AccountJson(Account account) {
            this.account = account;
        }
}
    @Override
    public RestfulEntity<?> getMe(Authentication authentication) {
        String username = authentication.getName();
        String role = accountService.getRoleByUsername(username);
        Account account = new Account().setRole(role).setUsername(username);


        RestfulEntity<?> entity;
        switch (role) {
            case EnumRole.TEACHER:
                Teacher teacher = teacherService.getTeacherByUsername(username);
                teacher.setAccount(account);
                entity = RestfulEntity.successMessage(
                        "success", teacher);
                break;
            case EnumRole.ADMIN:
                entity = RestfulEntity.successMessage(
                        "success", new AccountJson(account));
                break;
            case EnumRole.STUDENT:
            case EnumRole.STUDENT_REPRESENTATIVE:
                Student student = studentService.getStudentByUsername(username);
                student.setAccount(account);
                entity = RestfulEntity.successMessage(
                        "success", student);
                break;
            default:
                entity = RestfulEntity.failMessage(500, "未知角色");
        }
        return entity;
    }
}
