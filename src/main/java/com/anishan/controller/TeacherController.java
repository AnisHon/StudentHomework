package com.anishan.controller;

import com.anishan.entity.*;
import com.anishan.service.TeacherService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TeacherController {

    @Resource
    TeacherService teacherService;

    @ResponseBody
    @PostMapping("/admin/add-teacher")
    public String addTeacher (
            @Valid ParamAccount paramAccount,
            @Valid ParamTeacher paramTeacher
    ) {
        Account account = new Account(paramAccount);
        Teacher teacher = new Teacher(paramTeacher);

        return teacherService.addPersonnelControllerTool(account, teacher);
    }

    @ResponseBody
    @GetMapping("/admin/teachers/{page}")
    public String getStudents(
            @Min(value = 1, message = "页号从1开始")
            @PathVariable
            Integer page,
            String search
    ) {
        List<Teacher> teachers = teacherService.getPagedTeacherInfo(page, search);
        return RestfulEntity.successMessage("success", teachers).toJsonWithoutNull();
    }

    @ResponseBody
    @GetMapping("/teacher/me")
    public String getMyselfTeacher(Authentication authentication) {
        String username = authentication.getName();
        Teacher teacher = teacherService.getTeacherByUsername(username);
        return RestfulEntity.successMessage("success", teacher).toJson();
    }

}
