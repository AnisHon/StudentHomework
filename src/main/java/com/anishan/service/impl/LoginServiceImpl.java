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
    public String getValidationCode() {
        UUID uuid = UUID.randomUUID();
        SpecCaptcha captcha = new SpecCaptcha(130, 48);

        CaptchaObject object = new CaptchaObject();
        object.setImage(captcha.toBase64()).setUuid(uuid.toString());

        stringRedisTemplate.opsForValue().set("user:" + uuid + ":code", captcha.text(), TIME_OUT, TimeUnit.SECONDS);

        return RestfulEntity.successMessage("success", object).toJson();
    }

    @Override
    public boolean validateCode(String uuid, String code) {
        String s = stringRedisTemplate.opsForValue().get("user" + uuid + ":code");
        if (s == null || s.isEmpty()) {
            return false;
        }
        return s.equals(code);
    }
}
