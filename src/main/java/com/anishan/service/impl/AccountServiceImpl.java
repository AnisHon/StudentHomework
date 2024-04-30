package com.anishan.service.impl;

import com.anishan.entity.Account;
import com.anishan.entity.Student;
import com.anishan.mapper.AccountMapper;
import com.anishan.service.AccountService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {


    @Resource
    AccountMapper accountMapper;

    @Override
    public void addStudent(Account account, Student student) {

    }

    @Override
    public void addAccount(Account account) {
        accountMapper.insertAccount(account);
    }
}
