package com.anishan.service;

import com.anishan.entity.Account;
import com.anishan.entity.Student;

public interface AccountService {

    void addStudent(Account account, Student student);

    void addAccount(Account account);

    String getEmail(String username);

    boolean changePassword(String username, String newPassword);

    boolean changePasswordByEmail(String email, String newPassword);
}
