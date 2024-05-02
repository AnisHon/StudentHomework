package com.anishan;

import com.anishan.entity.*;
import com.anishan.mapper.AccountMapper;
import com.anishan.mapper.StudentMapper;
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

    @Test
    public void studentMapperTest() {
        Student student = new Student();
        Account account = new Account();
        account.setUsername("s114514");
        account.setPassword(passwordEncoder.encode("Han123456"));
        account.setRole("student");
        account.setEmail(null);
        student.setAccount(account);
        student.setName("卢本伟");
        student.setAge(10);
        Clazz clazz = new Clazz();
        clazz.setId(1);
        student.setClazz(clazz);
        student.setAddress("深海的大菠萝里");
        student.setGender("男");
        accountMapper.insertAccount(account);
        studentMapper.insertStudent(student);


    }


}
