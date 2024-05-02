package com.anishan.service;

public interface EmailService {

    boolean sendEmailVerificationCode(String email, String sessionId, String subject);

    void changeEmail(String email, String username);

    boolean validateEmailVerificationCode(String sessionId, String reqCode);

    String getEmail(String sessionId);

}
