package com.anishan.exceptionHandler;

import com.anishan.entity.RestfulEntity;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.stream.Collectors;

@RestController
@ControllerAdvice
public class PageExceptionHandler {
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public String handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {

        return RestfulEntity
                .failMessage(
                        HttpStatus.BAD_REQUEST.value(),
                        "错误请求：" + ex.getPropertyName() + "不匹配"
                ).toJson();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String message = ex
                .getAllErrors()
                .stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(" "));
        return RestfulEntity
                .failMessage(
                        HttpStatus.BAD_REQUEST.value(),
                        message
                ).toJson();
    }



    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseBody
    public String handleNoResourceFoundException() {
        return RestfulEntity.failMessage(404, "页面不存在").toJson();
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseBody
    public String handleMethodValidationException(HandlerMethodValidationException ex) {
        String message = ex
                .getAllErrors()
                .stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(" "));
        return RestfulEntity.failMessage(400, message).toJson();
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(Exception ex) {
        System.out.println(ex.getClass().getName());
        return RestfulEntity.failMessage(500, ex.getMessage()).toJson();
    }





}


