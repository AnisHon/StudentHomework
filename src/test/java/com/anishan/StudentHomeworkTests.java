package com.anishan;

import com.anishan.entity.Account;
import com.anishan.entity.CaptchaObject;
import com.anishan.entity.RestfulEntity;
import com.anishan.mapper.AccountMapper;
import com.anishan.service.EmailService;
import com.anishan.tool.EmailCodeSender;
import com.wf.captcha.SpecCaptcha;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static com.wf.captcha.base.Captcha.TYPE_DEFAULT;

@SpringBootTest
@Slf4j
class StudentHomeworkTests {

    @Resource
    PasswordEncoder passwordEncoder;
    @Resource
    AccountMapper accountMapper;



    @Test
    public void contextLoads() {

        try {
            Account account = new Account();
            account.setRole("teacher");
            account.setUsername("t114514");
            account.setPassword(passwordEncoder.encode("Han123456"));

            accountMapper.insertAccount(account);
        } catch (DuplicateKeyException e) {
            log.warn(e.getMessage());
        }


    }

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void captchaTest() {
        SpecCaptcha captcha = new SpecCaptcha(130, 48);
        captcha.setCharType(TYPE_DEFAULT);
        System.out.println(captcha.toBase64());
    }

    @Test
    public void captchaTest2() {
        UUID uuid = UUID.randomUUID();
        SpecCaptcha captcha = new SpecCaptcha(130, 48);
        captcha.setCharType(TYPE_DEFAULT);

        CaptchaObject object = new CaptchaObject();
        object.setImage(captcha.toBase64()).setUuid(uuid.toString());

        stringRedisTemplate.opsForValue().set("user:" + uuid + ":code", captcha.text());

        System.out.println(RestfulEntity.successMessage("success", object).toJson());
    }


    @Resource
    EmailCodeSender emailCodeSender;
    @Test
    public void testEmailSender() {
        emailCodeSender.sendCode("3137687133@qq.com", "测试", 6);
    }


    @Resource
    EmailService emailService;
    @Test
    public void testEmailService() {
        boolean b = emailService.sendEmailVerificationCode("3137687133@qq.com", "1", "测试");
        String email = emailService.getEmail("1");
        System.out.println(email);
        System.out.println(b);
        System.out.println(emailService.validateEmailVerificationCode("1", "vhA08w"));
    }

}
