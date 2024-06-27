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

@ResponseBody
@Controller
public class StudentController {

    private final int PRE_PAGE_SIZE = 10;

    @Resource
    StudentService studentService;


    @GetMapping(value = "/teacher/search-student/{name}", produces = "application/json")
    public String searchStudent(@PathVariable String name ) {
        List<Student> students = studentService.getStudentByName(name);
        return RestfulEntity.successMessage("success", students).toJsonWithoutNull();
    }

    /**
     * 获得多个学生信息（student + class + username）
     * @param page 页码
     */
    @GetMapping(value = "/teacher/students/{page}", produces = "application/json")
    public String getStudents(
            @Min(value = 1, message = "页号从1开始")
            @PathVariable
            Integer page,
            String search
    ) {

        List<Student> studentsInfo = studentService.getStudentsInfo(page, PRE_PAGE_SIZE, search);
        return RestfulEntity.successMessage("success", studentsInfo).toJsonWithoutNull();
    }

    /**
     * 查询单个学生信息
     * @param id 学生ID
     */
    @GetMapping(value = "/teacher/student/{id}", produces = "application/json")
    public String getStudent(
            @PathVariable
            Integer id
    ) {
        Student student = studentService.getStudentById(id);
        boolean b = (student != null);
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
    @PostMapping(value = "/admin/add-student", produces = "application/json")
    public String addStudent(
            @Valid ParamAccount paramAccount,
            @Valid ParamStudent paramStudent,
            @NotNull(message = "必须有班级ID") Integer classId
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


    @GetMapping(value = "/admin/delete-student/{id}", produces = "application/json")
    public String deleteStudent(@PathVariable Integer id) {
        boolean b = studentService.deleteStudent(id);
        return RestfulEntity.boolMessage(b, "success", 404, "ID不存在").toJson();

    }

    /**
     * 获得学生个人信息（所有）
     */
    @GetMapping(value = "/student/me", produces = "application/json")
    public String getMyStudentInfo(Authentication authentication) {
        String username = authentication.getName();
        Student student = studentService.getStudentByUsername(username);
        return RestfulEntity.successMessage("success", student).toJson();
    }



}
