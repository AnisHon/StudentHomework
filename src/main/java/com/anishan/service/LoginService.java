package com.anishan.service;

public interface LoginService {


    long TIME_OUT = 60;

    String getValidationCode(String sessionId);

    boolean validateCode(String uuid, String code);


}
