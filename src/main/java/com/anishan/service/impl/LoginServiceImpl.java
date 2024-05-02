package com.anishan.service.impl;

import com.anishan.entity.CaptchaObject;
import com.anishan.entity.RestfulEntity;
import com.anishan.service.LoginService;
import com.wf.captcha.SpecCaptcha;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.wf.captcha.base.Captcha.TYPE_DEFAULT;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


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
}
