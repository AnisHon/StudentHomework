package com.anishan.controller;

import com.anishan.entity.RestfulEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StudentController {

    @RequestMapping("/student")
    @ResponseBody
    public String getStudent() {
        return RestfulEntity.successMessage("Test", "Test").toJson();
    }

}
