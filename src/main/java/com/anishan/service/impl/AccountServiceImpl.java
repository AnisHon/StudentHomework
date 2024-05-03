package com.anishan.service.impl;

import com.anishan.entity.Account;
import com.anishan.entity.RestfulEntity;
import com.anishan.entity.Teacher;
import com.anishan.mapper.AccountMapper;
import com.anishan.mapper.TeacherMapper;
import com.anishan.service.AccountService;
import com.anishan.tool.EnumRole;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {


    @Resource
    PasswordEncoder encoder;
    @Resource
    AccountMapper accountMapper;
    @Resource
    private TeacherMapper teacherMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addAccount(Account account) {
        account.setPassword(encoder.encode(account.getPassword()));
        accountMapper.insertAccount(account);
    }

    @Override
    public String getEmail(String username) {
        return accountMapper.selectEmailByUsername(username);
    }

    @Override
    public boolean changePassword(String username, String newPassword) {
        int i = accountMapper.updatePasswordByUsername(username, encoder.encode(newPassword));
        return i >= 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean changePasswordByEmail(String email, String newPassword) {
        int i = accountMapper.updatePasswordByEmail(email, encoder.encode(newPassword));
        return i >= 1;
    }

    @Override
    public String getRoleByUsername(String username) {
        return username == null ? null : accountMapper.selectRoleByUsername(username);
    }


}
