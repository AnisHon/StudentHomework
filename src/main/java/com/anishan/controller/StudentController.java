package com.anishan.controller;

import com.anishan.entity.*;
import com.anishan.service.StudentService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StudentController {

    private final int PRE_PAGE_SIZE = 10;

    @Resource
    StudentService studentService;


    /**
     * 获得多个学生信息（student + class）
     * @param page 页码
     */
    @GetMapping("/teacher/students/{page}")
    @ResponseBody
    public String getStudents(
            @Min(value = 1, message = "页号从1开始")
            @PathVariable
            Integer page
    ) {
        List<Student> studentsInfo = studentService.getStudentsInfo(page, PRE_PAGE_SIZE);
        return RestfulEntity.successMessage("success", studentsInfo).toJson();
    }

    /**
     * 查询单个学生信息
     * @param id 学生ID
     */
    @GetMapping("/teacher/student/{id}")
    @ResponseBody
    public String getStudent(
            @PathVariable
            Integer id
    ) {
        Student student = studentService.getStudentById(id);

        boolean b = student == null;
        return RestfulEntity.boolMessage(b, "success", student, 404, "ID不存在").toJson();
    }

    /**
     * 添加学生，包括账号
     * @param paramAccount
     *      username 账号
     *      password 密码（自动加密）
     * @param paramStudent
     *      name 名字
     *      age 年龄（0-100）
     *      gender 性别（男/女）
     *      address 地址
     * @param classId 班级ID
     * @return 错误信息JSON或成功信息
     */
    @ResponseBody
    @PostMapping("/admin/add-student")
    public String addStudent(
            @Valid ParamAccount paramAccount,
            @Valid ParamStudent paramStudent,
            @NotNull Integer classId
    ) {
        Account account = new Account(paramAccount);
        Student student = new Student(paramStudent);

        return studentService.addPersonnelControllerTool(account, student, classId);
//        try {
//            boolean b = studentService.addStudent(account, student, classId);
//            if (b) {
//                return RestfulEntity.successMessage("success", "").toJson();
//            }
//        } catch (ConflictExcption e) {
//            return RestfulEntity.failMessage(409, e.getMessage()).toJson();
//        }
//
//        return RestfulEntity.failMessage(400, "添加失败，未知错误").toJson();
    }


    @ResponseBody
    @GetMapping("/admin/delete-student/{id}")
    public String deleteStudent(@PathVariable Integer id) {
        boolean b = studentService.deleteStudent(id);
        return RestfulEntity.boolMessage(b, "success", 404, "ID不存在").toJson();

    }

    /**
     * 获得学生个人信息（所有）
     */
    @ResponseBody
    @GetMapping("/student/me")
    public String getMyStudentInfo(Authentication authentication) {
        String username = authentication.getName();
        Student student = studentService.getStudentByUsername(username);
        System.out.println(student);
        return RestfulEntity.successMessage("success", student).toJson();
    }
}
