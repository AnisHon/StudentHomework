package com.anishan.controller;

import com.anishan.entity.Clazz;
import com.anishan.entity.RestfulEntity;
import com.anishan.entity.Subject;
import com.anishan.mapper.SubjectMapper;
import com.anishan.service.ResourceAssignmentService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ResouceController {

    @Resource
    ResourceAssignmentService resourceService;
    @Autowired
    private SubjectMapper subjectMapper;


    @GetMapping(value = "/student/searchClass/{className}", produces = "application/json")
    public String classSuggestion(@PathVariable String className) {
        List<Clazz> clazzes = resourceService.selectClassByName(className);
        return RestfulEntity.successMessage("success", clazzes).toJsonWithoutNull();
    }

    @GetMapping(value = "/student/search-subject/{subjectName}", produces = "application/json")
    public String subjectSuggestion(@PathVariable String subjectName) {
        List<Subject> subjects = subjectMapper.selectSubjectNameLikeSearch(subjectName);
        return RestfulEntity.successMessage("success", subjects).toJson();
    }

    @PostMapping(value = "/admin/add-class",produces = "application/json")
    public String addClass(@NotEmpty String name) {
        boolean b = resourceService.insertClass(name);
        return RestfulEntity.boolMessage(b, "success", 400, "fail").toJson();
    }

    @GetMapping(value = "/teacher/classes/{page}", produces = "application/json")
    public String getClass(
            @Min(value = 1, message = "不得小于1")
            @PathVariable
            int page, String search) {
        List<Clazz> clazzes = resourceService.selectLimitedClass(page, search);
        return RestfulEntity.successMessage("success", clazzes).toJsonWithoutNull();
    }

    @PostMapping(value = "/admin/change-class", produces = "application/json")
    public String changeClass(@Valid Clazz clazz) {
        boolean b = resourceService.updateClass(clazz);
        return RestfulEntity.boolMessage(b).toJson();
    }

    @GetMapping(value = "/admin/delete-class/{id}", produces = "application/json")
    public String deleteClass(@PathVariable int id) {
        boolean b = resourceService.removeClass(id);
        return RestfulEntity.boolMessage(b, "success", 400, "ID不存在").toJson();
    }


    @PostMapping(value = "/admin/add-subject", produces = "application/json")
    public String addSubject(@NotEmpty String name) {
        boolean b = resourceService.insertSubject(name);
        return RestfulEntity.boolMessage(b, "success", 400, "fail").toJson();
    }

    @GetMapping(value = "/student/subjects/{page}", produces = "application/json")
    public String getSubjects(
            @Min(value = 1, message = "不得小于1")
            @PathVariable
            int page, String search) {
        List<Subject> subjects = resourceService.selectLimitedSubject(page, search);
        return RestfulEntity.successMessage("success", subjects).toJsonWithoutNull();
    }

    @PostMapping(value = "/admin/change-subject", produces = "application/json")
    public String changeSubject(@Valid Subject subject) {
        boolean b = resourceService.updateSubject(subject);
        return RestfulEntity.boolMessage(b).toJson();
    }

    @GetMapping(value = "/admin/delete-subject/{id}", produces = "application/json")
    public String deleteSubject(@PathVariable int id) {
        boolean b = resourceService.deleteSubjectById(id);
        return RestfulEntity.boolMessage(b, "success", 400, "ID不存在").toJson();
    }

    @GetMapping(value = "/teacher/search-subject/{subjectName}", produces = "application/json")
    public String searchSubject(@PathVariable String subjectName) {
        List<Subject> subjects = resourceService.selectSubjectsByName(subjectName);
        return RestfulEntity.successMessage("success", subjects).toJson();
    }


}
