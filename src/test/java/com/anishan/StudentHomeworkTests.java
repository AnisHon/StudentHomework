package com.anishan;

import com.anishan.entity.*;
import com.anishan.mapper.AccountMapper;
import com.anishan.mapper.StudentMapper;
import com.anishan.service.ScoreService;
import com.anishan.service.StudentService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

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

    @Resource
    ScoreService scoreService;

    @Test
    public void studentMapperTest() {
        List<Score> pagedScore = scoreService.getPagedScore(1);
        System.out.println(pagedScore);
    }


}
