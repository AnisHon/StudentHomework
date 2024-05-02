package com.anishan.service.impl;

import com.anishan.entity.Account;
import com.anishan.entity.Student;
import com.anishan.mapper.AccountMapper;
import com.anishan.service.AccountService;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {


    @Resource
    PasswordEncoder encoder;
    @Resource
    AccountMapper accountMapper;

    @Override
    public void addStudent(Account account, Student student) {

    }

    @Override
    public void addAccount(Account account) {
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
    public boolean changePasswordByEmail(String email, String newPassword) {
        int i = accountMapper.updatePasswordByEmail(email, encoder.encode(newPassword));
        return i >= 1;
    }


}
