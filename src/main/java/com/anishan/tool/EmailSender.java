package com.anishan.tool;

import jakarta.annotation.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Component
public class EmailSender {

   @Resource
   private JavaMailSender sender;
   @Resource
   private RandomCode randomCode;

   private static final String EMAIL_FROM = "a13920111@163.com";


   public String sendCode(String email, String subject, int length) {
       String code = randomCode.getCode(length);
       sendMessage(email, subject, "重置验证码:" + code);
       return code;
   }

   public void sendMessage(String email, String subject, String content) {
       SimpleMailMessage message = new SimpleMailMessage();
       message.setSubject(subject);
       message.setTo(email);
       message.setFrom(EMAIL_FROM);
       message.setText(content);
       sender.send(message);
   }



}
