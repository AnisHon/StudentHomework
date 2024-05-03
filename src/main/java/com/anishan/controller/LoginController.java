package com.anishan.controller;

import com.alibaba.fastjson2.JSON;
import com.anishan.entity.Account;
import com.anishan.entity.RestfulEntity;
import com.anishan.entity.Student;
import com.anishan.entity.Teacher;
import com.anishan.service.*;
import com.anishan.service.impl.StudentServiceImpl;
import com.anishan.tool.EnumRole;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
public class LoginController {


    @Resource
    private LoginService loginService;

    @Resource
    private EmailService emailService;

    @Resource
    private AccountService accountService;
    @Resource
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;

    /**
     * 图形验证码
     * @return 返回的是base64编码的验证码
     */
    @RequestMapping(value = {"/user/code"}, method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
    @ResponseBody
    public String getCode(HttpSession session) {
        return loginService.getValidationCode(session.getId());
    }

    /**
     * 通过账号发送电子邮件验证码（可能没有设置账号导致发不出去）
     * @param username 账号
     */
    @ResponseBody
    @PostMapping("/user/send-reset-code")
    public String sendCode(
            @NotEmpty(message = "请输入用户名") String username,
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

    @ResponseBody
    @RequestMapping("/user/me")
    public String getMyselfTeacher(Authentication authentication) {
        if (authentication == null) {
            return RestfulEntity.successMessage("匿名用户", null).toJson();
        }

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
                        "success", account);
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
        return entity.toJsonWithoutNull();
    }

}
