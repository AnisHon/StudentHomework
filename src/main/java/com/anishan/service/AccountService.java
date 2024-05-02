package com.anishan.service;

import com.anishan.entity.Account;

public interface AccountService {


    void addAccount(Account account);

    String getEmail(String username);

    boolean changePassword(String username, String newPassword);

    boolean changePasswordByEmail(String email, String newPassword);
}
