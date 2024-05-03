package com.anishan.controller;

import com.anishan.entity.Clazz;
import com.anishan.entity.RestfulEntity;
import com.anishan.entity.Subject;
import com.anishan.service.ResourceAssignmentService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ResouceController {

    @Resource
    ResourceAssignmentService resourceService;

    @ResponseBody
    @PostMapping("/admin/add-class")
    public String addClass(@NotEmpty String name) {
        boolean b = resourceService.insertClass(name);
        return RestfulEntity.boolMessage(b, "success", 400, "fail").toJson();
    }

    @ResponseBody
    @GetMapping("/teacher/classes/{page}")
    public String getClass(
            @Min(value = 1, message = "不得小于1")
            @PathVariable
            int page, String search) {
        List<Clazz> clazzes = resourceService.selectLimitedClass(page, search);
        return RestfulEntity.successMessage("success", clazzes).toJsonWithoutNull();
    }

    @ResponseBody
    @PostMapping("/admin/change-class")
    public String changeClass(@Valid Clazz clazz) {
        boolean b = resourceService.updateClass(clazz);
        return RestfulEntity.boolMessage(b).toJson();
    }

    @ResponseBody
    @GetMapping("/admin/delete-class/{id}")
    public String deleteClass(@PathVariable int id) {
        boolean b = resourceService.removeClass(id);
        return RestfulEntity.boolMessage(b, "success", 400, "ID不存在").toJson();
    }


    @ResponseBody
    @PostMapping("/admin/add-subject")
    public String addSubject(@NotEmpty String name) {
        boolean b = resourceService.insertSubject(name);
        return RestfulEntity.boolMessage(b, "success", 400, "fail").toJson();
    }

    @ResponseBody
    @GetMapping("/teacher/subjects/{page}")
    public String getSubjects(
            @Min(value = 1, message = "不得小于1")
            @PathVariable
            int page, String search) {
        List<Subject> subjects = resourceService.selectLimitedSubject(page, search);
        return RestfulEntity.successMessage("success", subjects).toJsonWithoutNull();
    }

    @ResponseBody
    @PostMapping("/admin/change-subject")
    public String changeSubject(@Valid Subject subject) {
        boolean b = resourceService.updateSubject(subject);
        return RestfulEntity.boolMessage(b).toJson();
    }

    @ResponseBody
    @GetMapping("/admin/delete-subject/{id}")
    public String deleteSubject(@PathVariable int id) {
        boolean b = resourceService.deleteSubjectById(id);
        return RestfulEntity.boolMessage(b, "success", 400, "ID不存在").toJson();
    }

    @ResponseBody
    @GetMapping("/teacher/search-subject/{subjectName}")
    public String searchSubject(@PathVariable String subjectName) {
        List<Subject> subjects = resourceService.selectSubjectsByName(subjectName);
        return RestfulEntity.successMessage("success", subjects).toJson();
    }


}
