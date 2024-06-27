package com.anishan.controller;

import com.anishan.entity.Account;
import com.anishan.entity.RestfulEntity;
import com.anishan.entity.Student;
import com.anishan.entity.Teacher;
import com.anishan.service.*;
import com.anishan.tool.EnumRole;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
public class LoginController {


    @Resource
    private LoginService loginService;

    @Resource
    private EmailService emailService;

    @Resource
    private AccountService accountService;
    @Resource
    private TeacherService teacherService;
    @Resource
    private StudentService studentService;

    /**
     * 图形验证码
     * @return 返回的是base64编码的验证码
     */
    @RequestMapping(value = {"/user/code"}, method =
            {RequestMethod.GET, RequestMethod.POST}
    )
    @ResponseBody
    public String getCode(HttpSession session) {
        return loginService.getValidationCode(session.getId());
    }

    /**
     * 通过账号发送电子邮件验证码（可能没有设置账号导致发不出去）
     * @param username 账号
     */
    @PostMapping(value = "/user/send-reset-code", produces = "application/json")
    public String sendCode(
            @NotEmpty(message = "请输入账号") String username,
            HttpSession session) {

        String email = accountService.getEmail(username);
        if (email == null || email.isEmpty()) {
            return RestfulEntity.failMessage(400, "未设置邮箱请联系学生办").toJson();
        }
        boolean isSent = emailService.sendEmailVerificationCode(email, session.getId(), "重置密码");
        return RestfulEntity.boolMessage(
                isSent,
                "success",
                400, "请不要多次发送验证码").toJson();
    }


    /**
     * 通过验证码检测，并设置密码
     * @param password 新密码
     * @param reqCode 验证码
     */
    @ResponseBody
    @PostMapping("/user/reset-password")
    public String resetPassword(
            @Length(message = "密码{org.hibernate.validator.constraints.Length.message}", min = 8, max = 16)
            String password,
            @Length(message = "验证码{org.hibernate.validator.constraints.Length.message}", min = 6, max = 6)
            String reqCode,
            HttpSession session) {
        String email = emailService.getEmail(session.getId());
        boolean b = emailService.validateEmailVerificationCode(session.getId(), reqCode);

        if (!b) {
            return RestfulEntity.failMessage(401, "验证码错误").toJson();
        }
        b = accountService.changePasswordByEmail(email ,password);
        return RestfulEntity.boolMessage(b, "success", 400, "出现错误未更改").toJson();
    }

    @RequestMapping(value = "/user/me", produces = "application/json")
    public String getMyself(Authentication authentication) {
        if (authentication == null) {
            return RestfulEntity.successMessage("匿名用户", null).toJson();
        }
        RestfulEntity<?> me = loginService.getMe(authentication);
        return me.toJson();
    }




}
