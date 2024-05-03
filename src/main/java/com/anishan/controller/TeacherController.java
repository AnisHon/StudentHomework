package com.anishan.controller;

import com.anishan.entity.*;
import com.anishan.service.TeacherService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
//        try {
//            boolean b = teacherService.addTeacher(account, teacher);
//            if (b) {
//                return RestfulEntity.successMessage("success", "").toJson();
//            }
//        } catch (ConflictExcption e) {
//            return RestfulEntity.failMessage(409, e.getMessage()).toJson();
//        }
//
//        return RestfulEntity.failMessage(400, "添加失败，未知错误").toJson();
    }

}
