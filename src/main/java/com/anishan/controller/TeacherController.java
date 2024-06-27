package com.anishan.controller;

import com.anishan.entity.*;
import com.anishan.service.TeacherService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeacherController {

    @Resource
    TeacherService teacherService;

    @GetMapping("/admin/delete-teacher/{id}")
    public String deleteTeacher(@PathVariable Integer id) {
        teacherService.deleteTeacherById(id);
        return RestfulEntity.plainSuccessMessage("success").toJson();
    }

    @GetMapping(value = "/admin/search-teacher/{id}", produces = "application/json")
    public String searchTeacher(@PathVariable Integer id) {
        Teacher teacher = teacherService.getTeacherById(id);
        return RestfulEntity.boolMessage(
                teacher != null,
                "success", teacher,
                404, "不存在").toJson();
    }

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
    @GetMapping(value = "/admin/teachers/{page}", produces = "application/json")
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
    @GetMapping(value = "/teacher/me", produces = "application/json")
    public String getMyselfTeacher(Authentication authentication) {
        String username = authentication.getName();
        Teacher teacher = teacherService.getTeacherByUsername(username);
        return RestfulEntity.successMessage("success", teacher).toJson();
    }

}
