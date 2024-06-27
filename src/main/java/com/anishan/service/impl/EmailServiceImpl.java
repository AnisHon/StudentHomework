package com.anishan.service.impl;

import com.anishan.mapper.AccountMapper;
import com.anishan.service.EmailService;
import com.anishan.tool.EmailSender;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class EmailServiceImpl implements EmailService {

    @Resource
    EmailSender codeSender;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    AccountMapper accountMapper;


    private static final int SECOUND_TIME_OUT = 60;
    private static final int LENTH = 6;
    private String getEmailKey(String sessionId) {
        return "user:" + sessionId + ":email";
    }

    private String getCodeKey(String email) {
        return "email:" + email + ":code";
    }

    private String resendKey(String sessionId) {
        return "user:" + sessionId + ":resend";
    }
    private static Lock lock = new ReentrantLock();

    /**
     * 该方法只负责发送验证码和阻止重复发送验证码
     * 该方法会存储 email:{email}:code 和 user:{sessionId}:email
     * 前者用于存储 邮箱验证码，后者存储 session相关邮箱
     * @param email     向该邮箱发送验证码
     * @param sessionId session.getSessionId()防止刷验证码
     * @param subject   发送邮件的标题
     * @return 表示是否发送成功
     *  true: 发送成功
     *  false: 发送失败，由于重复发送
     */
    public boolean sendEmailVerificationCode(String email, String sessionId, String subject) {
        String resendKey = resendKey(sessionId);
        String emailKey = getEmailKey(sessionId);
        String codeKey = getCodeKey(email);

        lock.lock();;
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(resendKey))
                || Boolean.TRUE.equals(stringRedisTemplate.hasKey(codeKey))
        ) {
            lock.unlock();
            return false;
        }
        String code = codeSender.getCode(LENTH);
        stringRedisTemplate.opsForValue().set(resendKey, "1", SECOUND_TIME_OUT, TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue().set(emailKey, email, 2 * SECOUND_TIME_OUT, TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue().set(codeKey, code, 2 * SECOUND_TIME_OUT, TimeUnit.SECONDS);

        codeSender.sendMessage(email, subject, "重置验证码:" + code);
        lock.unlock();
        return true;
    }



    /**
     *
     * @param email 改成这个email
     * @param username 修改username的email
     */
    @Override
    public void changeEmail(String email, String username) {

    }

    /**
     * 校验是否正确
     * 如果成功匹配会删除所有的邮箱key和验证码key
     * @param sessionId 获取key必备
     * @param reqCode   请求参数 验证码
     * @return 是否正确
     */
    @Override
    public boolean validateEmailVerificationCode(String sessionId, String reqCode) {
        String emailKey = getEmailKey(sessionId);
        String email = stringRedisTemplate.opsForValue().get(emailKey);
        if (email == null || email.isEmpty()) {
            return false;
        }
        String codeKey = getCodeKey(email);
        String code = stringRedisTemplate.opsForValue().get(codeKey);
        boolean result = code != null && code.equals(reqCode);

        if (result) {
            stringRedisTemplate.delete(codeKey);
            stringRedisTemplate.delete(emailKey);
        }
        return result;
    }

    @Override
    public String getEmail(String sessionId) {
        String emailKey = getEmailKey(sessionId);
        return stringRedisTemplate.opsForValue().get(emailKey);
    }
}
