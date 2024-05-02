package com.anishan;

import com.anishan.entity.*;
import com.anishan.mapper.AccountMapper;
import com.anishan.mapper.StudentMapper;
import com.anishan.service.StudentService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Slf4j
class StudentHomeworkTests {

    @Resource
    PasswordEncoder passwordEncoder;
    @Resource
    AccountMapper accountMapper;


    @Resource
    StudentMapper studentMapper;

    @Resource
    StudentService studentService;

    @Test
    public void studentMapperTest() {

        Student student = studentMapper.selectStudentByUsername("s114514");
        System.out.println(student);
    }


}
