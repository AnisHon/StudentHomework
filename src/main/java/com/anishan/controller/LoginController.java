package com.anishan.controller;

import com.anishan.service.LoginService;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.Session;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class LoginController {


    @Resource
    LoginService loginService;

    @RequestMapping(value = {"/user/code"}, method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
    @ResponseBody
    public String getCode() throws IOException {
        return loginService.getValidationCode();
    }


}
