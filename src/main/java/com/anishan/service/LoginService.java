package com.anishan.service;

public interface LoginService {


    long TIME_OUT = 60;

    String getValidationCode();

    boolean validateCode(String uuid, String code);


}
