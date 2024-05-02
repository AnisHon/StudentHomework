package com.anishan.tool;

import jakarta.annotation.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Component
public class EmailCodeSender {

   @Resource
   private JavaMailSender sender;
   @Resource
   private RandomCode randomCode;
   private final SimpleMailMessage message;


    public EmailCodeSender() {
      message = new SimpleMailMessage();
      message.setSubject("");
   }



   public String sendCode(String email, String subject, int length) {
      String EMAIL_FROM = "a13920111@163.com";

      String code = randomCode.getCode(length);
      message.setSubject(subject);
      message.setTo(email);
      message.setFrom(EMAIL_FROM);
      message.setText(code);
      sender.send(message);
      return code;
   }




}
