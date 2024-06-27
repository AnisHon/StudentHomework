package com.anishan.tool;

import jakarta.annotation.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
public class EmailSender {

   @Resource
   private JavaMailSender sender;
   @Resource
   private RandomCode randomCode;



   private static final String EMAIL_FROM = "a13920111@163.com";

   public String getCode(int length) {

       return randomCode.getCode(length);
   }

   @Async
   public void sendMessage(String email, String subject, String content) {
       SimpleMailMessage message = new SimpleMailMessage();
       message.setSubject(subject);
       message.setTo(email);
       message.setFrom(EMAIL_FROM);
       message.setText(content);
       sender.send(message);
   }



}
