package com.anishan.service;

import com.anishan.entity.RestfulEntity;
import org.springframework.security.core.Authentication;

public interface LoginService {


    long TIME_OUT = 60;

    String getValidationCode(String sessionId);

    boolean validateCode(String uuid, String code);

    RestfulEntity<?> getMe(Authentication authentication);

}
