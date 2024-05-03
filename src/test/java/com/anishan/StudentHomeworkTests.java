package com.anishan;

import com.anishan.entity.*;
import com.anishan.exception.ConflictExcption;
import com.anishan.mapper.AccountMapper;
import com.anishan.mapper.ScoreMapper;
import com.anishan.mapper.StudentMapper;
import com.anishan.service.ScoreService;
import com.anishan.service.StudentService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Resource
    ScoreService scoreService;
    @Autowired
    private ScoreMapper scoreMapper;

    @Test
    public void studentMapperTest() {
        Score score = new Score()
                .setScore(6000)
                .setSubject(new Subject().setId(123))
                .setStudent(new Student().setId(4));

        try {
            scoreService.addScore(score);
        } catch (ConflictExcption e) {
            System.out.println(e.getMessage());
        }
    }


}
