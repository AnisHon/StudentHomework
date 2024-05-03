package com.anishan.service.impl;

import com.anishan.entity.Account;
import com.anishan.entity.Teacher;
import com.anishan.exception.ConflictExcption;
import com.anishan.mapper.TeacherMapper;
import com.anishan.service.AccountService;
import com.anishan.service.TeacherService;
import com.anishan.tool.EnumRole;
import jakarta.annotation.Resource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Resource
    TeacherMapper teacherMapper;
    @Resource
    AccountService accountService;

    public boolean addTeacher(Account account, Teacher teacher) throws ConflictExcption {
        account.setRole(EnumRole.TEACHER);
        teacher.setAccount(account);

        try {
            accountService.addAccount(account);
        } catch (DuplicateKeyException e) {
            ConflictExcption conflictExcption = new ConflictExcption("账号："+ account.getUsername() + "冲突");
            conflictExcption.initCause(e);
            throw conflictExcption;
        }

        int i = teacherMapper.insertTeacher(teacher);
        return i >= 1;
    }



}
